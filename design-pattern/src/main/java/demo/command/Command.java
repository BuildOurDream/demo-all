package demo.command;

/**
 * <p>命令模式</p>
 *
 * @Author J.Star
 * @Date 2021-06-19
 */
public interface Command {

    /**
     * 执行
     */
    void execute();

    /**
     * 撤销
     */
    void undo();
}
