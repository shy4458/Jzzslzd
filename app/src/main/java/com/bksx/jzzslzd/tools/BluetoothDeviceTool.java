package com.bksx.jzzslzd.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.view.View;

import com.bksx.jzzslzd.common.StaticObject;

import java.util.Iterator;
import java.util.Set;

public class BluetoothDeviceTool implements View.OnLongClickListener {
	private Activity context;
	public final int REQUESTCODE = 0xf001;

	public BluetoothDeviceTool(Activity context) {
		this.context = context;
	}

	@Override
	public boolean onLongClick(View arg0) {
		// 得到BluetoothAdapter对象
		showBTAddressSelectDia();
		return true;
	}

	public void showBTAddressSelectDia() {
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

		// 判断BluetoothAdapter对象是否为空，如果为空，则表明本机没有蓝牙设备
		if (adapter != null) {
			adapter.enable();
			new Builder(context)
					.setTitle("提示")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setMessage("请选择您将进行的操作")
					.setPositiveButton("添加并匹配新设备",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// 如果蓝牙设备不可用的话,创建一个intent对象,该对象用于启动一个Activity,提示用户启动蓝牙适配器
									StaticObject.showToast(context,
											"请对新的蓝牙设备进行配对，配对完成请返回页面");
									Intent intent = new Intent(
											Settings.ACTION_BLUETOOTH_SETTINGS);
									context.startActivityForResult(intent,
											REQUESTCODE);
								}
							})
					.setNegativeButton("选择已匹配设备",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									selectBTAddress();
								}
							}).show();
		}
	}

	public void selectBTAddress() {
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		Set<BluetoothDevice> devices = adapter.getBondedDevices();
		final String[] addresses = new String[devices.size()];
		if (devices.size() > 0) {
			int i = 0;
			// 用迭代
			for (Iterator<BluetoothDevice> iterator = devices.iterator(); iterator
					.hasNext(); i++) {
				// 得到BluetoothDevice对象,也就是说得到配对的蓝牙适配器
				BluetoothDevice device = iterator.next();
				// 得到远程蓝牙设备的地址
				addresses[i] = device.getAddress();
			}
			new AlertDialog.Builder(context)
					.setIcon(android.R.drawable.ic_dialog_info)
					.setTitle("请选择蓝牙设备")
					.setItems(addresses, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							SharedPreferences preference = context
									.getSharedPreferences(
											StaticObject.SHAREPREFERENC,
											Activity.MODE_PRIVATE);
							preference
									.edit()
									.putString("login_address",
											addresses[which]).commit();
							StaticObject.showToast(context,
									"设备选择成功，请点击按钮进行扫描");
							System.out.println(preference.getString(
									"login_address", ""));
						}
					}).show();
		}else{
			StaticObject.showToast(context, "未找到已配对的蓝牙设备");
		}
	}
}