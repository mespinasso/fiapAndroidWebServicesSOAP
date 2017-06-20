package com.example.logonrm.fiapandroidksoup;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {

    String METHOD_NAME = "Soma";
    String SOAP_ACTION = "";

    String NAMESPACE = "http://mespinas.com.br/";
    String SOAP_URL = "http://10.3.1.23:8080/Calculadora/Calculadora?WSDL";

    ProgressDialog pdialog;

    private EditText etNum1;
    private EditText etNum2;

    private TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNum1 = (EditText) findViewById(R.id.etNum1);
        etNum2 = (EditText) findViewById(R.id.etNum2);

        tvResultado = (TextView) findViewById(R.id.tvResultado);
    }

    public void somar(View v) {
        int num1 = Integer.parseInt(etNum1.getText().toString());
        int num2 = Integer.parseInt(etNum2.getText().toString());

        SomarAsync somarAsync = new SomarAsync();
        somarAsync.execute(num1, num2);
    }

    private class SomarAsync extends AsyncTask<Integer, Void, String> {

        @Override
        protected String doInBackground(Integer... params) {
            SoapObject request;
            SoapPrimitive calcular = null;

            request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("numero1", params[0]);
            request.addProperty("numero2", params[1]);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE httpTransport = new HttpTransportSE(SOAP_URL);
            try {
                httpTransport.call(SOAP_ACTION, envelope);
                calcular = (SoapPrimitive) envelope.getResponse();
            } catch (Exception e) {
                e.getMessage();
            }

            if(calcular != null)
                return calcular.toString();

            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pdialog.dismiss();
            tvResultado.setText(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdialog = new ProgressDialog(MainActivity.this);
            pdialog.setMessage("Calculando...");
            pdialog.show();
        }
    }
}
