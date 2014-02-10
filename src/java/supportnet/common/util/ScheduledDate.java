package supportnet.common.util;

public class ScheduledDate{
	public final String date;
	public final String offset;
	
	public ScheduledDate(String date, String offset) {
		super();
		this.date = date;
		this.offset = offset;
	}

	@Override
	public String toString() {
		return "ScheduledDate [date=" + date + ", offset=" + offset + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((offset == null) ? 0 : offset.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScheduledDate other = (ScheduledDate) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (offset == null) {
			if (other.offset != null)
				return false;
		} else if (!offset.equals(other.offset))
			return false;
		return true;
	}
	
	
}