package bridge.exception;

public enum ErrorMessage {
    BRIDGE_LENGTH_EXCEPTION("다리 길이는 3부터 20 사이의 숫자여야 합니다."),
    CELL_TO_MOVE_FORMAT_BRIDGE_LENGTH_EXCEPTION("이동할 칸은 U 혹은 D로 입력해 주세요.");
    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
