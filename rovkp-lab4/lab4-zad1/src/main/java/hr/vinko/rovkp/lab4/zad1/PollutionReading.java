package hr.vinko.rovkp.lab4.zad1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PollutionReading implements Comparable<PollutionReading> {

	private final static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	private int ozone;
	private int particullateMatter;
	private int carbonMonoxide;
	private int sulfureDioxide;
	private int nitrogenDioxide;

	private double longitude;
	private double latitude;

	private Date timestamp;

	public PollutionReading(String reading) {
		this(reading.split(","));
	}

	public PollutionReading(String[] reading) {
		try {
			ozone = Integer.parseInt(reading[0]);
			particullateMatter = Integer.parseInt(reading[1]);
			carbonMonoxide = Integer.parseInt(reading[2]);
			sulfureDioxide = Integer.parseInt(reading[3]);
			nitrogenDioxide = Integer.parseInt(reading[4]);

			longitude = Double.parseDouble(reading[5]);
			latitude = Double.parseDouble(reading[6]);

			timestamp = DATE_FORMAT.parse(reading[7]);

		} catch (Exception e) {
			throw new IllegalArgumentException("Unparseable line!");
		}
	}

	public static boolean isParseable(String[] reading) {
		try {
			new PollutionReading(reading);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public int getOzone() {
		return ozone;
	}

	public int getParticullateMatter() {
		return particullateMatter;
	}

	public int getCarbonMonoxide() {
		return carbonMonoxide;
	}

	public int getSulfureDioxide() {
		return sulfureDioxide;
	}

	public int getNitrogenDioxide() {
		return nitrogenDioxide;
	}

	public double getLongitude() {
		return longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	@Override
	public int compareTo(PollutionReading o) {
		return timestamp.compareTo(o.timestamp);
	}

	@Override
	public String toString() {
		return ozone + "," + particullateMatter + "," + carbonMonoxide + "," + sulfureDioxide + "," + nitrogenDioxide
				+ "," + longitude + "," + latitude + "," + DATE_FORMAT.format(timestamp);
	}
	
	

}
