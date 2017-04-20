package fr.amu.univ.smartcalendar.utils;


import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import fr.amu.univ.smartcalendar.R;

public abstract class PermissionUtils {
    public static void requestPermission(AppCompatActivity activity, int requestId, String permission, boolean  finishActivity){
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)){
            PermissionUtils.RationalDialog.newInstance(requestId, finishActivity).show(activity.getSupportFragmentManager(), "dialog");
        }else{
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestId);
        }
    }

    public static boolean isPermissionGranted(String[] grantPermissions, int[] grantResults, String permission){
        for(int i = 0; i < grantPermissions.length; i++){
            if(permission.equals(grantPermissions[i])){
                return grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }
        }
        return false;
    }


    public static class PermissionDeniedDialog extends DialogFragment{
        private static final String ARGUMENT_FINISHED_ACTIVITY = "finished";

        private boolean isActivityFinished = false;

        public static PermissionDeniedDialog newInstance(boolean finishedActivity){
            Bundle arguments = new Bundle();
            arguments.putBoolean(ARGUMENT_FINISHED_ACTIVITY, finishedActivity);

            PermissionDeniedDialog dialog = new PermissionDeniedDialog();
            dialog.setArguments(arguments);
            return dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            isActivityFinished = getArguments().getBoolean(ARGUMENT_FINISHED_ACTIVITY);
            return new AlertDialog.Builder(getActivity()).setMessage(R.string.smart_calendar_location_permission_denied).setPositiveButton(android.R.string.ok, null).create();
        }

        @Override
        public void onDismiss(DialogInterface dialog){
            super.onDismiss(dialog);
            if(isActivityFinished){
                Toast.makeText(getActivity(), R.string.smart_calendar_permission_required_toast, Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }
    }


    public static class RationalDialog extends DialogFragment{
        private static final String ARGUMENT_PERMISSION_REQUEST_CODE = "request_code";

        private static final String ARGUMENT_FINISHED_ACTIVITY = "finished";

        private boolean isActivityFished = false;

        public static RationalDialog newInstance(int requestCode, boolean fishedActivity) {
            Bundle arguments = new Bundle();
            arguments.putInt(ARGUMENT_PERMISSION_REQUEST_CODE, requestCode);
            arguments.putBoolean(ARGUMENT_FINISHED_ACTIVITY, fishedActivity);
            RationalDialog dialog = new RationalDialog();
            dialog.setArguments(arguments);
            return dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            Bundle arguments = getArguments();
            final int requestCode = arguments.getInt(ARGUMENT_PERMISSION_REQUEST_CODE);
            isActivityFished = arguments.getBoolean(ARGUMENT_FINISHED_ACTIVITY);

            return new AlertDialog.Builder(getActivity()).setMessage(R.string.smart_calendar_permission_rational_location).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which){
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestCode);
                    isActivityFished = false;
                }
            }).setNegativeButton(android.R.string.cancel, null).create();
        }

        @Override
        public void onDismiss(DialogInterface dialog){
            super.onDismiss(dialog);
            if(isActivityFished){
                Toast.makeText(getActivity(), R.string.smart_calendar_permission_required_toast, Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }
    }
}


