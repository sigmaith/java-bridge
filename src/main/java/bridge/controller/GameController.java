package bridge.controller;

import bridge.domain.BridgeGame;
import bridge.exception.CustomException;
import bridge.exception.ErrorMessage;
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
        start(bridgeGame);
    }

    private void start(BridgeGame bridgeGame) {
        while (true) {
            if (crossAllTheBridges(bridgeGame)) { // 다리건너기 성공하면 출력하고 그만!
                outputView.printResult(bridgeGame, true);
                break;
            }
            String command = proceedWhenFailed(bridgeGame);
            if (command.equals("Q")) {
                break;
            }
        }
    }

    private String proceedWhenFailed(BridgeGame bridgeGame) {
        String gameCommand = retry(() -> getValidGameCommand());// 다시 시도할지 입력받기
        if (gameCommand.equals("R")) {
            bridgeGame.reset(); // 게임 리셋 (플레이어 다시 -1로 제자리)
        }
        if (gameCommand.equals("Q")) {
            outputView.printResult(bridgeGame, false); // 출력하고 그만
        }
        return gameCommand;
    }

    private String getValidGameCommand() {
        String input = inputView.readGameCommand();
        if (!input.equals("R") && !input.equals("Q")) {
            throw CustomException.from(ErrorMessage.GAME_COMMAND_EXCEPTION);
        }
        return input;
    }

    private boolean crossAllTheBridges(BridgeGame bridgeGame) {
        bridgeGame.increaseTryNumber();
        for (int i = 0; i < bridgeGame.getBridgeLength(); i++) {
            boolean moved = retry(() -> bridgeGame.move(inputView.readMoving()));
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
