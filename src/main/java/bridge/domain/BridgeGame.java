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

    public static BridgeGame from(String input) {
        validate(input); // 숫자인지, 3~20 인지 검증
        return new BridgeGame(Integer.parseInt(input));
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
    public void move() {
    }

    /**
     * 사용자가 게임을 다시 시도할 때 사용하는 메서드
     * <p>
     * 재시작을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void retry() {
    }

    /**
     * 다리의 길이를 입력 받아서 다리를 생성해주는 역할을 한다.
     */
    public static class BridgeMaker {

        private final BridgeRandomNumberGenerator bridgeNumberGenerator;

        public BridgeMaker(BridgeRandomNumberGenerator bridgeNumberGenerator) {
            this.bridgeNumberGenerator = bridgeNumberGenerator;
        }

        /**
         * @param size 다리의 길이
         * @return 입력받은 길이에 해당하는 다리 모양. 위 칸이면 "U", 아래 칸이면 "D"로 표현해야 한다.
         */
        public List<String> makeBridge(int size) {
            List<String> bridge = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                bridge.add(returnBridgeParse());
            }
            return bridge;
        }

        private String returnBridgeParse() {
            if (bridgeNumberGenerator.generate() == 0) {
                return "D";
            }
            return "U";
        }
    }
}