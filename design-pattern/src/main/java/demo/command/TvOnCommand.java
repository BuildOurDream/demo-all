package demo.command;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-19
 */
public class TvOnCommand implements Command {

    private TvReceiver tvReceiver;

    public TvOnCommand(TvReceiver tvReceiver) {
        this.tvReceiver = tvReceiver;
    }

    @Override
    public void execute() {
        tvReceiver.on();
    }

    @Override
    public void undo() {
        tvReceiver.off();
    }
}
