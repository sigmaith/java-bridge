package bridge.controller;

import bridge.domain.BridgeGame;
import bridge.view.InputView;
import bridge.view.OutputView;
import java.util.function.Supplier;

public class GameController {
    private final InputView inputView;
    private final OutputView outputView;

    public GameController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        inputView.announceStartOfGame();
        BridgeGame bridgeGame = retry(() -> BridgeGame.from(inputView.readBridgeSize()));
        while (true) {
            bridgeGame.increaseTryNumber();
            boolean moved = false;
            if (crossAllTheBridges(bridgeGame, moved)) { // 다리건너기 성공하면 출력하고 그만!
                outputView.printResult(bridgeGame, true);
                break;
            }
            String gameCommand = inputView.readGameCommand(); // 다시 시도할지 입력받기
            if (gameCommand.equals("R")) {
                 bridgeGame.reset(); // 게임 리셋 (플레이어 다시 -1로 제자리)
                continue;
            }
            if (gameCommand.equals("Q")) {
                outputView.printResult(bridgeGame, moved); // 출력하고 그만
                break;
            }
        }
    }

    private boolean crossAllTheBridges(BridgeGame bridgeGame, boolean moved) {
        for (int i = 0; i < bridgeGame.getBridgeLength(); i++) {
            moved = retry(() -> bridgeGame.move(inputView.readMoving()));
            outputView.printMap(bridgeGame, moved);
            if (!moved) {
                return false;
            }
        }
        bridgeGame.recordSuccessCrossing();
        return true;
    }

    private static <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
