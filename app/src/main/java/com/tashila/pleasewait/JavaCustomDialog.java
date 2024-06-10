package com.tashila.pleasewait;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class JavaCustomDialog extends PleaseWaitDialog {
    private Context context;

    private JavaCustomDialog() {}

    public JavaCustomDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().setOnDismissListener(dialog -> Toast.makeText(context, "Dialog dismissed", LENGTH_SHORT).show());
    }
}
