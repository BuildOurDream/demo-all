package test.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import test.common.GameRole;

import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-10-25
 */
public class NashornTest {

    public static Map<String, CompiledScript> compiledScriptMap = new HashMap<>();

    public static void main(String[] args) throws ScriptException, NoSuchMethodException, FileNotFoundException {
        NashornScriptEngine engine = (NashornScriptEngine) new NashornScriptEngineFactory().getScriptEngine("-doe", "--global-per-engine");
        SimpleScriptContext simpleScriptContext = new SimpleScriptContext();
        GameRole gameRole = new GameRole();
        gameRole.setId("111");
        gameRole.setName("gm1");
        simpleScriptContext.setAttribute("gm", gameRole, ScriptContext.ENGINE_SCOPE);
        engine.setContext(simpleScriptContext);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("input:");
            String s = scanner.nextLine();
            CompiledScript test = getCompiledScript(engine, "test");
            test.eval();
            System.out.println(gameRole);
            engine.invokeFunction("fun2", s);
        }
    }

    public static void testM1() {
        System.out.println("testM122");
    }

    public static CompiledScript getCompiledScript(NashornScriptEngine engine, String mark) throws ScriptException, FileNotFoundException {
        CompiledScript compiledScript = compiledScriptMap.get(mark);
        if (compiledScript == null) {
            compiledScript = engine.compile(new FileReader("F:\\project\\demo-all\\spring-boot-demo\\src\\main\\java\\test\\nashorn\\test.js"));
//            compiledScriptMap.put(mark, compiledScript);
        }
        return compiledScript;
    }
}
