package test.es.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.Nullable;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <p>ES客户端操作工具</p>
 *
 * @Author J.Star
 * @Date 2021-04-10
 */
@Slf4j
@Component
@NoArgsConstructor
public class EsUtil implements InitializingBean, DisposableBean {

    @Resource
    private RestHighLevelClient elasticsearchRestHighLevelClient;

    private static RestHighLevelClient esClient;

    /**
     * 依赖静态注入 方便作为工具类进行静态调用
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        esClient = elasticsearchRestHighLevelClient;
    }

    @Override
    public void destroy() throws Exception {
        esClient.close();
    }

    /**
     * 切换ES客户端
     *
     * @param restHighLevelClient
     */
    public static void setEsClient(RestHighLevelClient restHighLevelClient) {
        esClient = restHighLevelClient;
    }

    public static CreateIndexResponse createIndex(String index) throws IOException {
        return createIndex(index, null, null, null);
    }

    public static CreateIndexResponse createIndex(String index, XContentBuilder xContentBuilder) throws IOException {
        return createIndex(index, null, xContentBuilder, null);
    }

    /**
     * 创建索引
     * 可自定义项 1.index settings 2.index mappings 3.index alias
     *
     * @param index           索引
     * @param settings        配置
     * @param xContentBuilder 文档结构
     * @param alias           别名
     * @return
     * @throws IOException
     */
    public static CreateIndexResponse createIndex(String index, Settings settings, XContentBuilder xContentBuilder, Alias alias) throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        if (settings != null) {
            createIndexRequest.settings(settings);
        }
        if (xContentBuilder != null) {
            createIndexRequest.mapping(xContentBuilder);
        }
        if (alias != null) {
            createIndexRequest.alias(alias);
        }
        return esClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
    }

    /**
     * 查询索引是否存在
     *
     * @param index
     * @return
     * @throws IOException
     */
    public static boolean isIndexExists(String index) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest(index);
        return esClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
    }

    /**
     * 删除索引
     *
     * @param index
     * @return
     */
    public static boolean deleteIndex(String index) {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
        boolean delete = false;
        try {
            delete = esClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT).isAcknowledged();
        } catch (Exception e) {
            log.warn("Delete index failed. {}", e.getMessage());
        }
        return delete;
    }

    /**
     * 修改mapping 只能修改新属性的映射配置
     *
     * @param index
     * @param xContentBuilder
     * @throws IOException
     */
    public static void putMapping(String index, XContentBuilder xContentBuilder) throws IOException {
        PutMappingRequest putMappingRequest = new PutMappingRequest(index).source(xContentBuilder);
        esClient.indices().putMapping(putMappingRequest, RequestOptions.DEFAULT);
    }

    /**
     * 创建文档
     *
     * @param index     索引
     * @param id        文档id
     * @param content   保存内容
     * @param overwrite 存在相同id是否直接覆盖 默认为覆盖
     * @param timeValue 请求超时时间
     * @return
     */
    public static IndexResponse createDoc(String index, @Nullable String id, Object content, Boolean overwrite, TimeValue timeValue) throws IOException {
        //校验参数
        Assert.notBlank(index, "文档索引不能为空");
        Assert.isTrue(JSONUtil.isJson(JSONUtil.toJsonStr(content)), "文档内容必须为json格式数据 => {}", content);
        //创建请求
        IndexRequest request = new IndexRequest(index).id(id).source(JSONUtil.parseObj(content));
        //设置超时时间
        if (timeValue != null) {
            request.timeout(timeValue);
        }
        if (overwrite != null && !overwrite) {
            request.opType(DocWriteRequest.OpType.CREATE);
        }
        return esClient.index(request, RequestOptions.DEFAULT);
    }

    public static IndexResponse createDoc(String index, String id, Object content) throws IOException {
        return createDoc(index, id, content, null, null);
    }

    public static IndexResponse createDoc(String index, String id, Object content, TimeValue timeValue) throws IOException {
        return createDoc(index, id, content, null, timeValue);
    }

    /**
     * 查询文档
     *
     * @param index         索引
     * @param docId         文档id
     * @param includeFields 需要获取的字段 不传为获取全部字段
     * @return
     */
    public static GetResponse getDoc(String index, String docId, String[] includeFields) throws IOException {
        Assert.notBlank(index, "文档索引不能为空");
        Assert.notBlank(docId, "文档id不能为空");
        GetRequest getRequest = new GetRequest(index, docId);
        if (includeFields != null && includeFields.length != 0) {
            getRequest.fetchSourceContext(new FetchSourceContext(true, includeFields, Strings.EMPTY_ARRAY));
        }
        return esClient.get(getRequest, RequestOptions.DEFAULT);
    }

    public static GetResponse getDoc(String index, String docId) throws IOException {
        return getDoc(index, docId, null);
    }

    /**
     * 检查文档是否存在
     *
     * @param index
     * @param docId
     * @return
     */
    public static boolean checkDocExistence(String index, String docId) throws IOException {
        GetRequest getRequest = new GetRequest(index, docId)
                .fetchSourceContext(new FetchSourceContext(false))
                .storedFields("_none_");
        return esClient.exists(getRequest, RequestOptions.DEFAULT);
    }

    /**
     * 修改文档
     *
     * @param index
     * @param id
     * @param params
     * @return
     */
    public static UpdateResponse updateDoc(String index, String id, Object params) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(index, id)
                .doc(JSONUtil.parseObj(params), XContentType.JSON)
                //不存在则新建
                .docAsUpsert(true)
                //修改遇到冲突时重试次数
                .retryOnConflict(5);

        //或者使用脚本更新
        /*Script inline = new Script(ScriptType.INLINE, "painless",
                "ctx._source.field += params.count", params);*/
        //使用已保存的脚本
        /*Script stored = new Script(
                ScriptType.STORED, null, "increment-field", params)*/
        ;
        return esClient.update(updateRequest, RequestOptions.DEFAULT);
    }

    /**
     * 删除文档
     *
     * @param index
     * @param docId
     * @return
     */
    public static RestStatus deleteDoc(String index, String docId) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(index, docId);
        return esClient.delete(deleteRequest, RequestOptions.DEFAULT).status();
    }

    /**
     * 复制源索引的文档至目标索引
     *
     * @param destIndex     目标索引
     * @param sourceIndices 源索引
     * @return
     */
    public static BulkByScrollResponse reindex(String destIndex, String... sourceIndices) throws IOException {
        ReindexRequest reindexRequest = new ReindexRequest();
        reindexRequest.setSourceIndices(sourceIndices).setDestIndex(destIndex)
                //保存原索引文档的版本，现索引中存在的文档将被修改，不存在的将新建
                .setDestVersionType(VersionType.EXTERNAL)
                //直接使用源索引文档覆盖到目标索引
                .setDestOpType(DocWriteRequest.OpType.INDEX.getLowercase())
                .setAbortOnVersionConflict(false);
        return esClient.reindex(reindexRequest, RequestOptions.DEFAULT);
    }

    /**
     * 批量操作
     *
     * @param bulkRequest
     * @param async
     * @return
     * @throws IOException
     */
    public static boolean bulk(BulkRequest bulkRequest, boolean async) throws IOException {
        if (async) {
            esClient.bulkAsync(bulkRequest, RequestOptions.DEFAULT, new ActionListener<BulkResponse>() {
                @Override
                public void onResponse(BulkResponse bulkItemResponses) {
                    log.info("批量处理数据完毕", bulkItemResponses);
                }

                @Override
                public void onFailure(Exception e) {
                    e.printStackTrace();
                    log.error("处理数据异常，", e.getMessage());
                }
            });
            return true;
        }
        BulkResponse bulkResponse = esClient.bulk(bulkRequest, RequestOptions.DEFAULT);

        //可做扩展
//        for (BulkItemResponse bulkItemResponse : bulkResponse) {
//            DocWriteResponse itemResponse = bulkItemResponse.getResponse();
//            switch (bulkItemResponse.getOpType()) {
//                case INDEX:
//                case CREATE:
//                    IndexResponse indexResponse = (IndexResponse) itemResponse;
//                    break;
//                case UPDATE:
//                    UpdateResponse updateResponse = (UpdateResponse) itemResponse;
//                    break;
//                case DELETE:
//                    DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
//            }
//        }
        return !bulkResponse.hasFailures();
    }

    public static boolean bulk(BulkRequest bulkRequest) throws IOException {
        return bulk(bulkRequest, false);
    }

    /**
     * 条件搜索
     *
     * @param searchRequest
     * @return
     */
    public static SearchResponse searchByConditions(SearchRequest searchRequest) throws IOException {
        return esClient.search(searchRequest, RequestOptions.DEFAULT);
    }

}
