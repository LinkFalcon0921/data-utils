package data.utils.holders.desktop;

public class SimpleDataHolder<H> extends DataHolder<H> {
    public SimpleDataHolder(H value) {
        super(value);
    }

    /**{@inheritDoc}*/
    @Override
    protected H copyOf(H data) {
        return data;
    }
}
