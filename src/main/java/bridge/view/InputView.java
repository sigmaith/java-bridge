package bridge.view;

import static camp.nextstep.edu.missionutils.Console.readLine;

/**
 * 사용자로부터 입력을 받는 역할을 한다.
 */
public class InputView {
    private final String BRIDGE_GAME_START_GUIDE = "다리 건너기 게임을 시작합니다.\n";
    private final String BRIDGE_LENGTH_INPUT_GUIDE = "다리의 길이를 입력해주세요.";
    private final String CELL_TO_MOVE_CHOICE_GUIDE = "이동할 칸을 선택해주세요. (위: U, 아래: D)";
    private final String GAME_COMMAND_INPUT_GUIDE = "게임을 다시 시도할지 여부를 입력해주세요. (재시도: R, 종료: Q)";

    public void announceStartOfGame() {
        System.out.println(BRIDGE_GAME_START_GUIDE);
    }

    /**
     * 다리의 길이를 입력받는다.
     */
    public String readBridgeSize() {
        System.out.println(BRIDGE_LENGTH_INPUT_GUIDE);
        return readLine();
    }

    /**
     * 사용자가 이동할 칸을 입력받는다.
     */
    public String readMoving() {
        System.out.println(CELL_TO_MOVE_CHOICE_GUIDE);
        return readLine();
    }

    /**
     * 사용자가 게임을 다시 시도할지 종료할지 여부를 입력받는다.
     */
    public String readGameCommand() {
        System.out.println(GAME_COMMAND_INPUT_GUIDE);
        return readLine();
    }
}
