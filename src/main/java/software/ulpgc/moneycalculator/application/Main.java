package software.ulpgc.moneycalculator.application;

import software.ulpgc.moneycalculator.architecture.control.ExchangeMoneyCommand;
import software.ulpgc.moneycalculator.architecture.control.SwapCurrenciesCommand;

public class Main {
    public static void main(String[] args) {
        Desktop desktop = new Desktop(new WebService.CurrencyLoader().loadAll());
        desktop.addCommand("exchange", new ExchangeMoneyCommand(
                desktop.moneyDialog(),
                desktop.currencyToDialog(),
                new WebService.ExchangeRateLoader(),
                desktop.moneyDisplay()
        ));
        desktop.addCommand("swap", new SwapCurrenciesCommand(
                desktop.currencyFromDialog(),
                desktop.currencyToDialog(),
                desktop.currencyFromDisplay(),
                desktop.currencyToDisplay()
        ));
        desktop.setVisible(true);
    }
}
