package cn.ymex.cuteact.permission;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.annotation.NonNull;


/**
 * PermissionRequest
 */

public class PermissionRequest {

    private String[] mRequestPermissions;
    private Dispatcher dispatcher;
    private int mRequestCode;
    private Context context;
    private Object target;


    private PermissionRequest() {
        super();
    }

    private PermissionRequest(Activity activity) {
        this.context = activity.getBaseContext();
        this.target = activity;
    }

    private PermissionRequest(Fragment fragment) {
        this.context = fragment.getActivity().getBaseContext();
        this.target = fragment;
    }

    private PermissionRequest(android.support.v4.app.Fragment fragment) {
        this.context = fragment.getActivity().getBaseContext();
        this.target = fragment;
    }

    public static PermissionRequest build(Activity act) {
        return new PermissionRequest(act);
    }

    public static PermissionRequest build(Fragment fragment) {
        return new PermissionRequest(fragment);
    }

    public static PermissionRequest build(android.support.v4.app.Fragment fragment) {
        return new PermissionRequest(fragment);
    }


    public PermissionRequest permissions(String... permissions) {
        this.mRequestPermissions = permissions;
        return this;
    }

    public PermissionRequest requestCode(int code) {
        this.mRequestCode = code;
        return this;
    }


    public boolean checkPermission(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
        return checkPermission();
    }


    private boolean checkPermission() {

        if (this.target == null || mRequestPermissions == null || mRequestPermissions.length <= 0) {
            throw new IllegalArgumentException("build permissionsRequest first !!!!");
        }

        if (PermissionHelper.hasSelfPermissions(context, mRequestPermissions)) {
            if (null != dispatcher) {
                dispatcher.onPermissionGranted(mRequestCode);
            }

            return true;
        }

        if (target instanceof Activity) {
            if (PermissionHelper.shouldShowRequestPermissionRationale((Activity) target, mRequestPermissions)) {
                if (null != dispatcher) {
                    dispatcher.onShowPermissionRationale(mRequestCode);
                }
            } else {
                requestPermissions();
            }
        } else if (target instanceof android.support.v4.app.Fragment) {
            if (PermissionHelper.shouldShowRequestPermissionRationale((android.support.v4.app.Fragment) target, mRequestPermissions)) {
                if (null != dispatcher) {
                    dispatcher.onShowPermissionRationale(mRequestCode);
                }
            } else {
                requestPermissions();
            }
        } else if (target instanceof Fragment) {
            if (PermissionHelper.shouldShowRequestPermissionRationale((Fragment) target, mRequestPermissions)) {
                if (null != dispatcher) {
                    dispatcher.onShowPermissionRationale(mRequestCode);
                }
            } else {
                requestPermissions();
            }
        } else {
            throw new IllegalArgumentException("not allow Type");
        }

        return false;
    }

    public void requestPermissions() {
        if (target instanceof Activity) {
            PermissionHelper.requestPermissions((Activity) target, mRequestPermissions, mRequestCode);
        } else if (target instanceof Fragment) {
            PermissionHelper.requestPermissions((Fragment) target, mRequestPermissions, mRequestCode);
        } else if (target instanceof android.support.v4.app.Fragment) {
            PermissionHelper.requestPermissions((android.support.v4.app.Fragment) target, mRequestPermissions, mRequestCode);
        } else {
            throw new IllegalArgumentException("not allow Type");
        }

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {
        if (PermissionHelper.verifyPermissions(grantResults)) {
            if (null != dispatcher) {
                dispatcher.onPermissionGranted(requestCode);
            }
        } else {

            if (target instanceof Activity) {
                if (!PermissionHelper.shouldShowRequestPermissionRationale((Activity) target, mRequestPermissions)) {
                    if (null != dispatcher) {
                        dispatcher.OnNeverAskPermission(requestCode);
                    }
                }
            } else if (target instanceof Fragment) {
                if (!PermissionHelper.shouldShowRequestPermissionRationale((Fragment) target, mRequestPermissions)) {
                    if (null != dispatcher) {
                        dispatcher.OnNeverAskPermission(requestCode);
                    }
                }
            } else if (target instanceof android.support.v4.app.Fragment) {
                if (!PermissionHelper.shouldShowRequestPermissionRationale((android.support.v4.app.Fragment) target, mRequestPermissions)) {
                    if (null != dispatcher) {
                        dispatcher.OnNeverAskPermission(requestCode);
                    }
                }
            }
            if (null != dispatcher) {
                dispatcher.onPermissionDenied(requestCode);
            }
        }
    }


    /**
     * jump to app setting
     */
    public void startAppSettings() {
        PermissionHelper.startAppSettings(this.context);
    }

    public interface Dispatcher {
        void onShowPermissionRationale(int requestCode);

        void onPermissionGranted(int requestCode);

        void onPermissionDenied(int requestCode);

        void OnNeverAskPermission(int requestCode);

    }


}

