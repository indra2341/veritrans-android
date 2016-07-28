package id.co.veritrans.sdk.sample.core;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import id.co.veritrans.sdk.coreflow.core.TransactionRequest;
import id.co.veritrans.sdk.coreflow.core.VeritransSDK;
import id.co.veritrans.sdk.coreflow.models.BillInfoModel;
import id.co.veritrans.sdk.coreflow.models.ItemDetails;
import id.co.veritrans.sdk.sample.BCAPaymentActivity;
import id.co.veritrans.sdk.sample.CIMCBClickPaymentActivity;
import id.co.veritrans.sdk.sample.CreditCardPaymentActivity;
import id.co.veritrans.sdk.sample.IndomaretPaymentActivity;
import id.co.veritrans.sdk.sample.MandiriBillPaymentActivity;
import id.co.veritrans.sdk.sample.MandiriClickPaymentActivity;
import id.co.veritrans.sdk.sample.PermataVAPaymentActivity;
import id.co.veritrans.sdk.sample.R;
import id.co.veritrans.sdk.sample.utils.RecyclerItemClickListener;
/**
 * @author rakawm
 */
public class CoreFlowActivity extends AppCompatActivity {
    private RecyclerView coreMethods;
    private CoreFlowListAdapter adapter;
    private ProgressDialog dialog = new ProgressDialog(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core_flow);
        dialog.setIndeterminate(true);
        dialog.setMessage("get payment methods");
        dialog.show();
        requestPaymentToken();
//        init();
    }

    private void requestPaymentToken() {
        String orderId = UUID.randomUUID().toString();
        TransactionRequest request = new TransactionRequest(orderId, 360000);
        // Set item details
        ItemDetails itemDetails = new ItemDetails("1", 360000, 1, "shoes");
        ArrayList<ItemDetails> itemDetailsArrayList = new ArrayList<>();
        itemDetailsArrayList.add(itemDetails);
        request.setItemDetails(itemDetailsArrayList);
        // Set Bill Info
        request.setBillInfoModel(new BillInfoModel("Bill Info Sample", "Bill Info Sample 2"));
        // Set transaction request
        VeritransSDK.getVeritransSDK().setTransactionRequest(request);

        VeritransSDK.getVeritransSDK().getSnapToken();
    }

    private void init() {
        coreMethods = (RecyclerView) findViewById(R.id.core_flow_list);

        // Init recycler view
        coreMethods.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CoreFlowListAdapter(generateCoreViewModels());
        coreMethods.setAdapter(adapter);
        coreMethods.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CoreViewModel coreViewModel = adapter.getItem(position);
                switch (coreViewModel.getImage()) {
                    case R.drawable.ic_credit:
                        goToActivity(new Intent(getApplicationContext(), CreditCardPaymentActivity.class));
                        break;
                    case R.drawable.ic_bca:
                        goToActivity(new Intent(getApplicationContext(), BCAPaymentActivity.class));
                        break;
                    case R.drawable.ic_permata:
                        goToActivity(new Intent(getApplicationContext(), PermataVAPaymentActivity.class));
                        break;
                    case R.drawable.ic_mandiri_bill_payment2:
                        goToActivity(new Intent(getApplicationContext(), MandiriBillPaymentActivity.class));
                        break;
                    case R.drawable.ic_mandiri2:
                        goToActivity(new Intent(getApplicationContext(), MandiriClickPaymentActivity.class));
                        break;
                    case R.drawable.ic_cimb:
                        goToActivity(new Intent(getApplicationContext(), CIMCBClickPaymentActivity.class));
                        break;
                    case R.drawable.ic_indomaret:
                        goToActivity(new Intent(getApplicationContext(), IndomaretPaymentActivity.class));
                        break;
                    default:
                        break;
                }
            }
        }));
    }

    private List<CoreViewModel> generateCoreViewModels() {
        List<CoreViewModel> models = new ArrayList<>();
        models.add(new CoreViewModel(getString(R.string.credit_card), R.drawable.ic_credit));
        models.add(new CoreViewModel(getString(R.string.bca_bank_transfer), R.drawable.ic_bca));
        models.add(new CoreViewModel(getString(R.string.permata_bank_transfer), R.drawable.ic_permata));
        models.add(new CoreViewModel(getString(R.string.mandiri_bill_payment), R.drawable.ic_mandiri_bill_payment2));
        models.add(new CoreViewModel(getString(R.string.mandiri_click_pay), R.drawable.ic_mandiri2));
        models.add(new CoreViewModel(getString(R.string.cimb_clicks), R.drawable.ic_cimb));
        models.add(new CoreViewModel(getString(R.string.indomaret), R.drawable.ic_indomaret));
        return models;
    }

    private void goToActivity(Intent intent) {
        startActivity(intent);
    }
}
