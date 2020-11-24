package io.github.andyalvarezdev.sugar.util;

import java.util.StringTokenizer;

public class Version implements Comparable
{
  private int
    major,
    sub,
    minor;

  public Version(int major, int sub) {
    this(major, sub, 0);
  }

  public Version(int major, int sub, int minor) {
    this.major=major;
    this.sub=sub;
    this.minor=minor;
  }

  public Version(String version) {
    major=0; sub=0; minor=0;
    StringTokenizer st = new StringTokenizer(version,".");
    if (st.hasMoreTokens()) major = Integer.parseInt(st.nextToken());
    if (st.hasMoreTokens()) sub = Integer.parseInt(st.nextToken());
    if (st.hasMoreTokens()) minor = Integer.parseInt(st.nextToken());
  }

  public String toString() {
    StringBuffer sb = new StringBuffer(8); // XX.YY.ZZ
    sb.append(major);
    sb.append('.');
    sb.append(sub);
    sb.append('.');
    sb.append(minor);
    return sb.toString();
  }

  public int getMajor() {
    return major;
  }

  public int getSub() {
    return sub;
  }

  public int getMinor() {
    return minor;
  }

  public int compareTo(Object o) {
    Version v = (Version)o;
    if (this.equals(v)) return 0;

    if (
         (major > v.getMajor()) ||
       ( (major == v.getMajor()) && (sub > v.getSub()) ) ||
       ( (major == v.getMajor()) && (sub == v.getSub()) && (minor > v.getMinor()) )
    ) return 1;
    else return -1;
  }

  public boolean equals(Object o) {
    if (!(o instanceof Version)) return false;
    Version v = (Version)o;
    return ((major == v.getMajor()) && (sub == v.getSub()) && (minor == v.getMinor()));
  }

  public int hashCode() {
     return major*10000+sub*100+minor;
  }

}
