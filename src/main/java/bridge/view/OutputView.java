package bridge.view;

import bridge.domain.BridgeGame;
import java.util.ArrayList;
import java.util.List;

/**
 * 사용자에게 게임 진행 상황과 결과를 출력하는 역할을 한다.
 */
public class OutputView {

    /**
     * 현재까지 이동한 다리의 상태를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void printMap(BridgeGame bridgeGame, boolean moved) {
        List<String> ul = new ArrayList<>(), dl = new ArrayList<>();

        if (moved) { // 방금거가 true면 걍 붙이면 되는데
            for (int idx = 0; idx < bridgeGame.getPlayerIndex(); idx++) {
                if (bridgeGame.getBridgePartBy(idx).equals("U")) { // U일때
                    ul.add(" O "); // upline에 " O " 추가
                    dl.add("   "); // downline에 "   " 추가
                }
                if (bridgeGame.getBridgePartBy(idx).equals("D")) { // D일때
                    ul.add("   "); // upline에 "   " 추가
                    dl.add(" O ");// downline에 " O " 추가
                }
            }

            if (bridgeGame.getBridgePartBy(bridgeGame.getPlayerIndex()).equals("U")) {
                ul.add(" O "); // upline에 " O " 추가
                dl.add("   "); // downline에 "   " 추가
            }
            if (bridgeGame.getBridgePartBy(bridgeGame.getPlayerIndex()).equals("D")) {
                ul.add("   "); // upline에 "   " 추가
                dl.add(" O ");// downline에 " O " 추가
            }
            System.out.println("[" + String.join("|", ul) + "]" + "\n" + "[" + String.join("|", dl) + "]\n");
            return;
        }
        // 방금거가 true가 아니면 X로 붙여야함.

        for (int idx = 0; idx <= bridgeGame.getPlayerIndex(); idx++) {
            if (bridgeGame.getBridgePartBy(idx).equals("U")) { // U일때
                ul.add(" O "); // upline에 " O " 추가
                dl.add("   "); // downline에 "   " 추가
            }
            if (bridgeGame.getBridgePartBy(idx).equals("D")) { // D일때
                ul.add("   "); // upline에 "   " 추가
                dl.add(" O ");// downline에 " O " 추가
            }
        }

        if (bridgeGame.getBridgePartBy(bridgeGame.getPlayerIndex() + 1).equals("U")) {
            ul.add("   ");
            dl.add(" X ");
        }
        if (bridgeGame.getBridgePartBy(bridgeGame.getPlayerIndex() + 1).equals("D")) {
            ul.add(" X ");
            dl.add("   ");
        }
        System.out.println("[" + String.join("|", ul) + "]" + "\n" + "[" + String.join("|", dl) + "]\n");
    }

    /**
     * 게임의 최종 결과를 정해진 형식에 맞춰 출력한다.
     * <p>
     * 출력을 위해 필요한 메서드의 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    public void printResult(BridgeGame bridgeGame, boolean moved) {
        System.out.println("최종 게임 결과");
        printMap(bridgeGame, moved);
        System.out.println();

        if (bridgeGame.success()) {
            System.out.println("게임 성공 여부: 성공");
        }

        if (!bridgeGame.success()) {
            System.out.println("게임 성공 여부: 실패");
        }

        System.out.println("총 시도한 횟수: " + bridgeGame.getTryNumber());
    }
}
