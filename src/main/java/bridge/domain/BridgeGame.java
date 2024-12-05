package bridge.domain;

import bridge.exception.CustomException;
import bridge.exception.ErrorMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 다리 건너기 게임을 관리하는 클래스
 */
public class BridgeGame {
    private final List<String> bridge;
    private boolean crossing = false;
    private int tryNumber = 0;
    private int playerIndex = -1;

    public static BridgeGame from(String input) {
        validate(input); // 숫자인지, 3~20 인지 검증
        return new BridgeGame(Integer.parseInt(input));
    }

    public int getBridgeLength() {
        return bridge.size();
    }

    public void increaseTryNumber() {
        tryNumber++;
    }

    public int getTryNumber() {
        return tryNumber;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public String getBridgePartBy(int index) {
        return bridge.get(index);
    }

    public void recordSuccessCrossing() {
        crossing = true;
    }

    public boolean success() {
        return crossing;
    }

    private BridgeGame(final int input) {
        BridgeMaker bridgeMaker = new BridgeMaker(new BridgeRandomNumberGenerator());
        this.bridge = bridgeMaker.makeBridge(input);
    }

    private static void validate(String input) {
        try {
            int bridgeLength = Integer.parseInt(input);
            if (bridgeLength < 3 || bridgeLength > 20) {
                throw CustomException.from(ErrorMessage.BRIDGE_LENGTH_EXCEPTION);
            }
        } catch (NumberFormatException e) {
            throw CustomException.from(ErrorMessage.BRIDGE_LENGTH_EXCEPTION);
        }
    }

    /**
     * 사용자가 칸을 이동할 때 사용하는 메서드
     * <p>
     * 이동을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public boolean move(String input) {
        validateUorD(input);
        if (bridge.get(playerIndex + 1).equals(input)) {
            playerIndex++;
            return true;
        }
        return false;
    }

    private void validateUorD(String moving) {
        if (!moving.equals("U") && !moving.equals("D")) {
            throw CustomException.from(ErrorMessage.CELL_TO_MOVE_FORMAT_BRIDGE_LENGTH_EXCEPTION);
        }
    }

    /**
     * 사용자가 게임을 다시 시도할 때 사용하는 메서드
     * <p>
     * 재시작을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void retry() {
    }

}
