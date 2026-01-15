package software.ulpgc.moneycalculator.architecture.control;

import software.ulpgc.moneycalculator.architecture.model.Currency;
import software.ulpgc.moneycalculator.architecture.ui.CurrencyDialog;
import software.ulpgc.moneycalculator.architecture.ui.CurrencyDisplay;

public class SwapCurrenciesCommand implements Command {
    private final CurrencyDialog from;
    private final CurrencyDialog to;
    private final CurrencyDisplay displayFrom;
    private final CurrencyDisplay displayTo;

    public SwapCurrenciesCommand(CurrencyDialog from, CurrencyDialog to, CurrencyDisplay displayFrom, CurrencyDisplay displayTo) {
        this.from = from;
        this.to = to;
        this.displayFrom = displayFrom;
        this.displayTo = displayTo;
    }

    @Override
    public void execute() {
        Currency currencyFrom = from.get();
        displayFrom.show(to.get());
        displayTo.show(currencyFrom);
    }
}
