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
            if (crossAllTheBridges(bridgeGame)) {
                // 다리건너기 성공하면 출력하고 그만!
                break;
            }
            String gameCommand = inputView.readGameCommand(); // 다시 시도할지 입력받기
            if (gameCommand.equals("R")) {
                // bridgeGame reset
                continue;
            }
            if (gameCommand.equals("Q")) {
                // 출력하고
                break;
            }
        }
    }

    private boolean crossAllTheBridges(BridgeGame bridgeGame) {
        for (int i = 0; i < bridgeGame.getBridgeLength(); i++) {
            Boolean moved = retry(() -> bridgeGame.move(inputView.readMoving()));
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
