package hr.vinko.rovkp.dz4.zad2;

import java.io.Serializable;

public class USBabyNameRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 904338437938047121L;
	
	
	private long id;
	private String name;
	private int year;
	private String gender;
	private String state;
	private long count;

	public USBabyNameRecord(String record) {
		this(record.split(","));
	}

	public USBabyNameRecord(String[] record) {
		try {
			this.id = Long.parseLong(record[0]);
			this.name = record[1];
			this.year = Integer.parseInt(record[2]);
			this.gender = record[3];
			this.state = record[4];
			this.count = Long.parseLong(record[5]);
		} catch (Exception e) {
			throw new IllegalArgumentException("Unparseable line!");
		}
	}
	
	public static boolean isParseable(String record) {
		try {
			new USBabyNameRecord(record);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getYear() {
		return year;
	}

	public String getGender() {
		return gender;
	}

	public String getState() {
		return state;
	}

	public long getCount() {
		return count;
	}

}
