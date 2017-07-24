package com.handheld.IDCard;

import android.graphics.Bitmap;

public class IDCardModel {
	private Bitmap bitmap;	//头像位图
	private String Name;	//名字
	private String Sex;		//性别
	private String Nation;	//民族
	private String Year;	//出生 年
	private String Month;	//月
	private String Day;		//日
	private String Address;	//住址
	private String Office;		//签发机关
	private String IDCardNumber;	//身份证号
	private String BeginTime;	//有效期开始时间
	private String EndTime;		//有效期结束时间
	private String OtherData;	//额外信息
	public IDCardModel()
	{
		super();
	};
	public IDCardModel(Bitmap bitmap,String Name,String Nation,String Sex,String Year,String Month,String Day,String Address
			, String Office,String IDCardNumber,String OtherData,String BeginTime,String EndTime,int IsOut)
	{
		super();
		this.bitmap = bitmap;
		this.Name = Name;
		this.Nation = Nation;
		this.Sex = Sex;
		this.Year = Year;
		this.Month = Month;
		this.Day = Day;
		this.Address = Address;
		this.Office =  Office;
		this.IDCardNumber = IDCardNumber;
		this.OtherData =  OtherData;
		this.BeginTime =  BeginTime;
		this.EndTime = EndTime;
	}
	public void setPhotoBitmap(Bitmap bitmap)
	{
		this.bitmap = bitmap;
	}
	public Bitmap getPhotoBitmap()
	{
		return bitmap;
	}
	public void setName(String Name_string)
	{
		this.Name = Name_string;

	}
	public String getName()
	{
		return Name;
	}
	public void setNation(String Nation)
	{
		this.Nation = Nation;

	}
	public String getNation()
	{
		return Nation;
	}
	public void setSex(String Sex)
	{
		this.Sex = Sex;

	}
	public String getSex()
	{
		return Sex;
	}
	public void setYear(String Year)
	{
		this.Year = Year;

	}
	public String getYear()
	{
		return Year;
	}
	public void setMonth(String Month)
	{
		this.Month = Month;

	}
	public String getMonth()
	{
		return Month;
	}
	public void setDay(String Day)
	{
		this.Day = Day;

	}
	public String getDay()
	{
		return Day;
	}public void setAddress(String Address)
	{
		this.Address = Address;

	}
	public String getAddress()
	{
		return Address;
	}
	public void setIDCardNumber(String IDCardNumber) {
		this.IDCardNumber = IDCardNumber;
	}
	public String getIDCardNumber() {
		return IDCardNumber;
	}
	public void setOffice(String Office) {
		this.Office = Office;
	}
	public String getOffice() {
		return Office;
	}
	public void setOtherData(String OtherData) {
		this.OtherData = OtherData;
	}
	public String getOtherData() {
		return OtherData;
	}
	public void setBeginTime(String BeginTime) {
		this.BeginTime = BeginTime;
	}
	public String getBeginTime() {
		return BeginTime;
	}
	public void setEndTime(String EndTime) {
		this.EndTime = EndTime;
	}
	public String getEndTime() {
		return EndTime;
	}
}
