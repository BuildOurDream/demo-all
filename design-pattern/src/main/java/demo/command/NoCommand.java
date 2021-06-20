package demo.command;

/**
 * <p>空命令 初始化 可以省去对空的判断</p>
 *
 * @Author J.Star
 * @Date 2021-06-19
 */
public class NoCommand implements Command{

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
