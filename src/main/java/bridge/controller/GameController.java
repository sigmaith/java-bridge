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
                break;
            }

            // 다시 시도할지 입력받기
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
