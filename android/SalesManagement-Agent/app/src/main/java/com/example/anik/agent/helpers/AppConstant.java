package com.example.anik.agent.helpers;

/**
 * Created by Anik on 05-Aug-15, 005.
 */
public class AppConstant {

    public static final String TAG = "com.example.anik.agent";

    public static final String DATABASE_NAME = "sales.db";
    public static final int DATABASE_VERSION = 1;
    public static final String USER_TYPE = "agent";

    public static final String APP_STORAGE_FOLDER = "sales_of_agent";
    public static final String MESSAGE_NO_INTERNET = "Device must be connected to the internet";
    public static final String MESSAGE_APP_ERROR = "Error occurred. Sorry for the inconvenience";
    public static final String MESSAGE_USER_ACCOUNT_CREATED = "User account is successfully created";
    public static final String MESSAGE_USER_CONFIRMATION_CODE_RESENT = "Your confirmation code is sent to your mobile number.";
    public static final String MESSAGE_USER_ACCOUNT_CONFIRMED = "User account is successfully confirmed";
    public static final String MESSAGE_DATA_PARSE_ERROR = "Error parsing data from internet";
    public static final String MESSAGE_FORM_NOT_FILLED_CORRECTLY = "You need to fill all the fields";
    public static final String MESSAGE_ = "";

    public static final String BROADCAST_ORDER = "com.example.anik.broadcast.Order";
    public static final String BROADCAST_QUICK_ORDER = "com.example.anik.broadcast.Quick";
    public static final String BROADCAST_BILL = "com.example.anik.broadcast.Bill";
    public static final String BROADCAST_LOCATION = "com.example.anik.broadcast.Location";
    public static final String BROADCAST_PHOTO_COMPLAINT = "com.example.anik.broadcast.PhotoComplaint";
    public static final String BROADCAST_TEXTUAL_COMPLAINT = "com.example.anik.broadcast.TextualComplaint";
    public static final String BROADCAST_USER_INFORMATION = "com.example.anik.broadcast.UserInformation";
    public static final String BROADCAST_PRODUCT_JSON_RECEIVER = "com.example.anik.broadcast.ProductJSONBroadcastListener";

    private static final String PROTOCOL = "http://";
    private static final String TEST_SERVER = PROTOCOL + "WEBSITE_ADDRESS";
    private static final String SERVER_URL = TEST_SERVER;
    private static final String BASE_URL = SERVER_URL;
    public static final String LINK_DUMMY = BASE_URL;
    public static final String LINK_LOGIN = BASE_URL + "login";
    public static final String LINK_CATEGORIES = BASE_URL + "categories/?";
    public static final String LINK_SUB_CATEGORIES = BASE_URL + "sub_categories/?";
    public static final String LINK_PRODUCTS = BASE_URL + "products/?";
    public static final String LINK_PRODUCT_SPECIFIC = BASE_URL + "products";
    public static final String LINK_PRODUCTS_ORDER = BASE_URL + "products/order/?";
    public static final String LINK_OFFERS = BASE_URL + "offers";
    public static final String LINK_NEWS = BASE_URL + "news";
    public static final String LINK_COMPLAINT = BASE_URL + "complaints/?";
    public static final String LINK_ORDER = BASE_URL + "orders";
    public static final String LINK_QUICK_ORDER = BASE_URL + "quick";
    public static final String LINK_TRACKER = BASE_URL + "trackers/?";
    public static final String LINK_USER_INFO = BASE_URL + "users";
    public static final String LINK_ASK = BASE_URL + "ask";
    public static final String LINK_ORDER_DETAILS = BASE_URL + "order_details";
    public static final String LINK_BILL = BASE_URL + "bills";
    public static final String LINK_REGISTRATION = BASE_URL + "users";
    public static final String LINK_CONFIRMATION = BASE_URL + "users/?";
    public static final String LINK_RESEND = BASE_URL + "resend";
    public static final String LINK_SHOP = BASE_URL + "shops/?";
    private static final String APP_SERVER = PROTOCOL + "mockup.in/sales-management/test/";
    private static int TIME_MILLISECONDS = 1;
    public static int TIME_SECONDS = 1000 * TIME_MILLISECONDS;
    public static int TIME_MINUTES = 60 * TIME_SECONDS;
}
