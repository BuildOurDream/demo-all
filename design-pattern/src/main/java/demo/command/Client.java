package demo.command;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-19
 */
public class Client {

    public static void main(String[] args) {
        LightReceiver lightReceiver = new LightReceiver();
        LightOnCommand lightOnCommand = new LightOnCommand(lightReceiver);
        LightOffCommand lightOffCommand = new LightOffCommand(lightReceiver);
        RemoteController remoteController = new RemoteController();
        remoteController.setCommand(1,lightOnCommand, lightOffCommand);
        remoteController.onButtonPush(1);
        remoteController.offButtonPush(1);
        remoteController.undo();

        TvReceiver tvReceiver = new TvReceiver();
        TvOnCommand tvOnCommand = new TvOnCommand(tvReceiver);
        TvOffCommand tvOffCommand = new TvOffCommand(tvReceiver);
        remoteController.setCommand(0, tvOnCommand, tvOffCommand);
        remoteController.onButtonPush(0);
        remoteController.offButtonPush(0);
        remoteController.undo();
    }
}
