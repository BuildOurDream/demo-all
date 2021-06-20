package demo.command;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-19
 */
public class TvOffCommand implements Command {

    private TvReceiver tvReceiver;

    public TvOffCommand(TvReceiver tvReceiver) {
        this.tvReceiver = tvReceiver;
    }

    @Override
    public void execute() {
        tvReceiver.off();
    }

    @Override
    public void undo() {
        tvReceiver.on();
    }
}
