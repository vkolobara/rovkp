package hr.vinko.rovkp.dz4.zad1;

public class SensorscopeReading implements Comparable<SensorscopeReading> {

	private long stationID;

	private int year;
	private int month;
	private int day;
	private int hour;
	private int min;
	private int sec;

	private long timeSinceEpoch;
	private long seqNumber;

	private double configSamplingTime;
	private double dataSamplingTime;
	private double radioDutyCycle;
	private double radioTransmissionPower;
	private double radioTransmissionFrequency;
	private double primaryBufferVoltage;
	private double secondaryBufferVoltage;
	private double solarPanelCurrent;
	private double globalCurrent;
	private double energySource;

	public SensorscopeReading(String line) {
		this(line.split("\\s+"));
	}

	public SensorscopeReading(String[] cols) {
		try {
			this.stationID = Long.parseLong(cols[0]);

			this.year = Integer.parseInt(cols[1]);
			this.month = Integer.parseInt(cols[2]);
			this.day = Integer.parseInt(cols[3]);
			this.hour = Integer.parseInt(cols[4]);
			this.min = Integer.parseInt(cols[5]);
			this.sec = Integer.parseInt(cols[6]);

			this.timeSinceEpoch = Long.parseLong(cols[7]);
			this.seqNumber = Long.parseLong(cols[8]);

			this.configSamplingTime = Double.parseDouble(cols[9]);
			this.dataSamplingTime = Double.parseDouble(cols[10]);
			this.radioDutyCycle = Double.parseDouble(cols[11]);
			this.radioTransmissionPower = Double.parseDouble(cols[12]);
			this.radioTransmissionPower = Double.parseDouble(cols[13]);
			this.primaryBufferVoltage = Double.parseDouble(cols[14]);
			this.secondaryBufferVoltage = Double.parseDouble(cols[15]);
			this.solarPanelCurrent = Double.parseDouble(cols[16]);
			this.globalCurrent = Double.parseDouble(cols[17]);
			this.energySource = Double.parseDouble(cols[18]);
		} catch (Exception e) {
			throw new IllegalArgumentException("Unparsable line");
		}

	}
	
	public static boolean isParseable(String line) {
		try {
			new SensorscopeReading(line);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	public long getStationID() {
		return stationID;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public int getMin() {
		return min;
	}

	public int getSec() {
		return sec;
	}

	public long getTimeSinceEpoch() {
		return timeSinceEpoch;
	}

	public long getSeqNumber() {
		return seqNumber;
	}

	public double getConfigSamplingTime() {
		return configSamplingTime;
	}

	public double getDataSamplingTime() {
		return dataSamplingTime;
	}

	public double getRadioDutyCycle() {
		return radioDutyCycle;
	}

	public double getRadioTransmissionPower() {
		return radioTransmissionPower;
	}

	public double getRadioTransmissionFrequency() {
		return radioTransmissionFrequency;
	}

	public double getPrimaryBufferVoltage() {
		return primaryBufferVoltage;
	}

	public double getSecondaryBufferVoltage() {
		return secondaryBufferVoltage;
	}

	public double getSolarPanelCurrent() {
		return solarPanelCurrent;
	}

	public double getGlobalCurrent() {
		return globalCurrent;
	}

	public double getEnergySource() {
		return energySource;
	}

	@Override
	public int compareTo(SensorscopeReading o) {
		return Double.compare(timeSinceEpoch, o.timeSinceEpoch);
	}

	@Override
	public String toString() {
		return stationID + "," + year + "," + month + "," + day + "," + hour + "," + min + "," + sec + ","
				+ timeSinceEpoch + "," + seqNumber + "," + configSamplingTime + "," + dataSamplingTime + ","
				+ radioDutyCycle + "," + radioTransmissionPower + "," + radioTransmissionFrequency + ","
				+ primaryBufferVoltage + "," + secondaryBufferVoltage + "," + solarPanelCurrent + "," + globalCurrent
				+ "," + energySource;
	}
	
	

}
