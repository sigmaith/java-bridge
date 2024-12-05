package bridge.view;

import bridge.domain.BridgeGame;
import java.util.ArrayList;
import java.util.List;

/**
 * 사용자에게 게임 진행 상황과 결과를 출력하는 역할을 한다.
 */
public class OutputView {
    private final String FINAL_GAME_RESULT = "최종 게임 결과";
    private final String GAME_SUCCESS = "게임 성공 여부: ";
    private final String SUCCESS = "성공";
    private final String FAILURE = "실패";
    private final String TOTAL_NUMBER_OF_ATTEMPTS = "총 시도한 횟수: %d\n";
    private final String EMPTY_SPACE = "   ";
    private final String SUCCESS_SPACE = " O ";
    private final String FAILURE_SPACE = " X ";

    /**
     * 현재까지 이동한 다리의 상태를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void printMap(BridgeGame bridgeGame, boolean moved) {
        List<String> ul = new ArrayList<>(), dl = new ArrayList<>();
        if (!moved) {
            printWhenFailed(bridgeGame, ul, dl);
            return;
        }
        printWhenSucceded(bridgeGame, ul, dl);
    }

    private void printWhenFailed(BridgeGame bridgeGame, List<String> ul, List<String> dl) {
        printUntilPlayerIndex(bridgeGame, ul, dl);
        printLastFailedPoint(bridgeGame, ul, dl);
        System.out.println("[" + String.join("|", ul) + "]" + "\n" + "[" + String.join("|", dl) + "]\n");
    }

    private void printLastFailedPoint(BridgeGame bridgeGame, List<String> ul, List<String> dl) {
        if (bridgeGame.getBridgePartBy(bridgeGame.getPlayerIndex() + 1).equals("U")) {
            ul.add(EMPTY_SPACE);
            dl.add(FAILURE_SPACE);
        }
        if (bridgeGame.getBridgePartBy(bridgeGame.getPlayerIndex() + 1).equals("D")) {
            ul.add(FAILURE_SPACE);
            dl.add(EMPTY_SPACE);
        }
    }

    private void printUntilPlayerIndex(BridgeGame bridgeGame, List<String> ul, List<String> dl) {
        for (int idx = 0; idx <= bridgeGame.getPlayerIndex(); idx++) { // 방금거가 true가 아니면 X로 붙여야함.
            if (bridgeGame.getBridgePartBy(idx).equals("U")) { // U일때
                ul.add(SUCCESS_SPACE); // upline에 " O " 추가
                dl.add(EMPTY_SPACE); // downline에 "   " 추가
            }
            if (bridgeGame.getBridgePartBy(idx).equals("D")) { // D일때
                ul.add(EMPTY_SPACE); // upline에 "   " 추가
                dl.add(SUCCESS_SPACE);// downline에 " O " 추가
            }
        }
    }

    private boolean printWhenSucceded(BridgeGame bridgeGame, List<String> ul, List<String> dl) {
        // 방금거가 true면 걍 붙이면 되는데
        printBeforePlayerIndex(bridgeGame, ul, dl);
        printLastSuccess(bridgeGame, ul, dl);
        System.out.println("[" + String.join("|", ul) + "]" + "\n" + "[" + String.join("|", dl) + "]\n");
        return true;
    }

    private void printLastSuccess(BridgeGame bridgeGame, List<String> ul, List<String> dl) {
        if (bridgeGame.getBridgePartBy(bridgeGame.getPlayerIndex()).equals("U")) {
            ul.add(SUCCESS_SPACE); // upline에 " O " 추가
            dl.add(EMPTY_SPACE); // downline에 "   " 추가
        }
        if (bridgeGame.getBridgePartBy(bridgeGame.getPlayerIndex()).equals("D")) {
            ul.add(EMPTY_SPACE); // upline에 "   " 추가
            dl.add(SUCCESS_SPACE);// downline에 " O " 추가
        }
    }

    private void printBeforePlayerIndex(BridgeGame bridgeGame, List<String> ul, List<String> dl) {
        for (int idx = 0; idx < bridgeGame.getPlayerIndex(); idx++) {
            if (bridgeGame.getBridgePartBy(idx).equals("U")) { // U일때
                ul.add(SUCCESS_SPACE); // upline에 " O " 추가
                dl.add(EMPTY_SPACE); // downline에 "   " 추가
            }
            if (bridgeGame.getBridgePartBy(idx).equals("D")) { // D일때
                ul.add(EMPTY_SPACE); // upline에 "   " 추가
                dl.add(SUCCESS_SPACE);// downline에 " O " 추가
            }
        }
    }

    /**
     * 게임의 최종 결과를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void printResult(BridgeGame bridgeGame, boolean moved) {
        System.out.println(FINAL_GAME_RESULT);
        printMap(bridgeGame, moved);
        System.out.println();
        if (bridgeGame.success()) {
            System.out.println(GAME_SUCCESS + SUCCESS);
        }
        if (!bridgeGame.success()) {
            System.out.println(GAME_SUCCESS + FAILURE);
        }
        System.out.printf(TOTAL_NUMBER_OF_ATTEMPTS, bridgeGame.getTryNumber());
    }
}
