package com.example.apple.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import com.example.apple.test.MainActivity;

/**
 * Created by Apple on 19/01/16.
 */
public class Responsemessage extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_response_message)

                .setNegativeButton(R.string.edit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //user cancelled the dialog
                    }
                });


        return builder.create();

    }
}
