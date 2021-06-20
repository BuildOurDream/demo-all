package demo.command;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-19
 */
public class RemoteController {

    /**
     * 开命令
     */
    private Command[] onCommands;

    /**
     * 关命令
     */
    private Command[] offCommands;

    private Command undoCommand;

    public RemoteController() {
        onCommands = new Command[5];
        offCommands = new Command[5];

        for (int i = 0; i < 5; i++) {
            //初始化一组空命令
            onCommands[i] = new NoCommand();
            offCommands[i] = new NoCommand();
        }
    }

    /**
     * 设置按键命令
     *
     * @param index
     * @param onCommand
     * @param offCommand
     */
    public void setCommand(int index, Command onCommand, Command offCommand) {
        onCommands[index] = onCommand;
        offCommands[index] = offCommand;
    }

    public void onButtonPush(int index) {
        //找到按下的开按钮,调用对应的方法
        onCommands[index].execute();
        //记录这次操作,用于撤销
        undoCommand = offCommands[index];
    }

    public void offButtonPush(int index) {
        //找到按下的关按钮,调用对应的方法
        offCommands[index].execute();
        //记录这次操作,用于撤销
        undoCommand = onCommands[index];
    }

    /**
     * 撤销操作
     */
    public void undo() {
        undoCommand.execute();
    }
}
