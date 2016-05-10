package itp341.mai.johnathan.koazie;

public class Result {

    private String mName, mAddress1, mAddress2, mPhone, mRating, mBeds;

    public Result() {
        mName = "";
        mAddress1 = "";
        mAddress2 = "";
        mPhone = "";
        mRating = "";
        mBeds = "";
    }

    public Result(String name, String address1, String address2, String phone, String rating, String beds) {
        mName = name;
        mAddress1 = address1;
        mAddress2 = address2;
        mPhone = phone;
        mRating = rating;
        mBeds = beds;
    }

    public String getName() {
        return mName;
    }

    public String getAddress1() {
        return mAddress1;
    }

    public String getAddress2() {
        return mAddress2;
    }

    public String getPhone() {
        return mPhone;
    }

    public String getRating() {
        return mRating;
    }

    public String getBeds() {
        return mBeds;
    }

    @Override
    public String toString() {
        return mName;
    }
}
