package cn.ymex.cute.permission;

/**
 * Created by ymexc on 2017/8/12.
 */

public abstract class PermissionDispatcher implements PermissionRequest.Dispatcher {


    @Override
    public abstract void onPermissionGranted(int requestCode);

    @Override
    public void onPermissionDenied(int requestCode) {

    }
    @Override
    public void onShowPermissionRationale(int requestCode) {

    }

    @Override
    public void OnNeverAskPermission(int requestCode) {

    }
}
