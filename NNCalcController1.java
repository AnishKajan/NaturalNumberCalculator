import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Controller class.
 *
 * @author Anish Kajan
 */
public final class NNCalcController1 implements NNCalcController {

    /**
     * Model object.
     */
    private final NNCalcModel model;

    /**
     * View object.
     */
    private final NNCalcView view;

    /**
     * Useful constants.
     */
    private static final NaturalNumber TWO = new NaturalNumber2(2),
            INT_LIMIT = new NaturalNumber2(Integer.MAX_VALUE);

    /**
     * Updates this.view to display this.model, and to allow only operations
     * that are legal given this.model.
     *
     * @param model
     *            the model
     * @param view
     *            the view
     * @ensures [view has been updated to be consistent with model]
     */
    private static void updateViewToMatchModel(NNCalcModel model, NNCalcView view) {

        NaturalNumber numTop = model.top();
        NaturalNumber numBottom = model.bottom();

        view.updateTopDisplay(numTop);
        view.updateBottomDisplay(numBottom);

        view.updateSubtractAllowed(numBottom.compareTo(numTop) <= 0);
        view.updateDivideAllowed(!numBottom.isZero());
        view.updateRootAllowed(
                numBottom.compareTo(TWO) >= 0 && numBottom.compareTo(INT_LIMIT) <= 0);
        view.updatePowerAllowed(numBottom.compareTo(TWO) >= 0);

    }

    /**
     * Constructor.
     *
     * @param model
     *            model to connect to
     * @param view
     *            view to connect to
     */
    public NNCalcController1(NNCalcModel model, NNCalcView view) {
        this.model = model;
        this.view = view;
        updateViewToMatchModel(model, view);
    }

    @Override
    public void processClearEvent() {
        /*
         * Get alias to bottom from model
         */
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        bottom.clear();
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processSwapEvent() {
        /*
         * Get aliases to top and bottom from model
         */
        NaturalNumber top = this.model.top();
        NaturalNumber bottom = this.model.bottom();
        /*
         * Update model in response to this event
         */
        NaturalNumber temp = top.newInstance();
        temp.transferFrom(top);
        top.transferFrom(bottom);
        bottom.transferFrom(temp);
        /*
         * Update view to reflect changes in model
         */
        updateViewToMatchModel(this.model, this.view);
    }

    @Override
    public void processEnterEvent() {

        NaturalNumber numTop = this.model.top();
        NaturalNumber numBottom = this.model.bottom();
        numTop.copyFrom(numBottom);

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddEvent() {

        NaturalNumber numTop = this.model.top();
        NaturalNumber numBottom = this.model.bottom();
        numTop.add(numBottom);
        numBottom.transferFrom(numTop);

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processSubtractEvent() {

        NaturalNumber numTop = this.model.top();
        NaturalNumber numBottom = this.model.bottom();
        numTop.subtract(numBottom);
        numBottom.transferFrom(numTop);

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processMultiplyEvent() {

        NaturalNumber numTop = this.model.top();
        NaturalNumber numBottom = this.model.bottom();
        numTop.multiply(numBottom);
        numBottom.transferFrom(numTop);

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processDivideEvent() {

        NaturalNumber numTop = this.model.top();
        NaturalNumber numBottom = this.model.bottom();
        NaturalNumber remainder = numTop.divide(numBottom);
        numBottom.transferFrom(numTop);
        numTop.transferFrom(remainder);

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processPowerEvent() {

        NaturalNumber numTop = this.model.top();
        NaturalNumber numBottom = this.model.bottom();
        int numPower = numBottom.toInt();
        numTop.power(numPower);

        numBottom.transferFrom(numTop);

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processRootEvent() {

        NaturalNumber numTop = this.model.top();
        NaturalNumber numBottom = this.model.bottom();
        int numRoot = numBottom.toInt();
        numTop.root(numRoot);
        numBottom.transferFrom(numTop);

        updateViewToMatchModel(this.model, this.view);

    }

    @Override
    public void processAddNewDigitEvent(int digit) {

        NaturalNumber numBottom = this.model.bottom();
        numBottom.multiplyBy10(digit);

        updateViewToMatchModel(this.model, this.view);

    }

}
