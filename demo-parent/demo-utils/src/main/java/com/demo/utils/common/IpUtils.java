package com.demo.utils.common;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * @author zzj
 * @create 2019-05-29 14:00
 **/
public class IpUtils {

    public static boolean isInternalIP(byte[] ip) {
        if (ip.length != 4) {
            throw new RuntimeException("illegal ipv4 bytes");
        }

        //10.0.0.0~10.255.255.255
        //172.16.0.0~172.31.255.255
        //192.168.0.0~192.168.255.255
        if (ip[0] == (byte) 10) {

            return true;
        } else if (ip[0] == (byte) 172) {
            if (ip[1] >= (byte) 16 && ip[1] <= (byte) 31) {
                return true;
            }
        } else if (ip[0] == (byte) 192) {
            if (ip[1] == (byte) 168) {
                return true;
            }
        }
        return false;
    }

    private static boolean ipCheck(byte[] ip) {
        if (ip.length != 4) {
            throw new RuntimeException("illegal ipv4 bytes");
        }

//        if (ip[0] == (byte)30 && ip[1] == (byte)10 && ip[2] == (byte)163 && ip[3] == (byte)120) {
//        }

        if (ip[0] >= (byte) 1 && ip[0] <= (byte) 126) {
            if (ip[1] == (byte) 1 && ip[2] == (byte) 1 && ip[3] == (byte) 1) {
                return false;
            }
            if (ip[1] == (byte) 0 && ip[2] == (byte) 0 && ip[3] == (byte) 0) {
                return false;
            }
            return true;
        } else if (ip[0] >= (byte) 128 && ip[0] <= (byte) 191) {
            if (ip[2] == (byte) 1 && ip[3] == (byte) 1) {
                return false;
            }
            if (ip[2] == (byte) 0 && ip[3] == (byte) 0) {
                return false;
            }
            return true;
        } else if (ip[0] >= (byte) 192 && ip[0] <= (byte) 223) {
            if (ip[3] == (byte) 1) {
                return false;
            }
            if (ip[3] == (byte) 0) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static String ipToIPv4Str(byte[] ip) {
        if (ip.length != 4) {
            return null;
        }
        return new StringBuilder().append(ip[0] & 0xFF).append(".").append(
                ip[1] & 0xFF).append(".").append(ip[2] & 0xFF)
                .append(".").append(ip[3] & 0xFF).toString();
    }

    public static byte[] getIP() {
        try {
            Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            byte[] internalIP = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();
                    if (ip != null && ip instanceof Inet4Address) {
                        byte[] ipByte = ip.getAddress();
                        if (ipByte.length == 4) {
                            if (ipCheck(ipByte)) {
                                if (!isInternalIP(ipByte)) {
                                    return ipByte;
                                } else if (internalIP == null) {
                                    internalIP = ipByte;
                                }
                            }
                        }
                    }
                }
            }
            if (internalIP != null) {
                return internalIP;
            } else {
                throw new RuntimeException("Can not get local ip");
            }
        } catch (Exception e) {
            throw new RuntimeException("Can not get local ip", e);
        }
    }

    public static String getLocalAddress() {
        try {
            try {
                // 获取当前机器上所有的网络接口
                Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
                ArrayList<String> ipv4Result = new ArrayList();
                ArrayList ipv6Result = new ArrayList();

                while(enumeration.hasMoreElements()) {
                    NetworkInterface networkInterface = (NetworkInterface)enumeration.nextElement();
                    Enumeration en = networkInterface.getInetAddresses();

                    while(en.hasMoreElements()) {
                        InetAddress address = (InetAddress)en.nextElement();
                        if (!address.isLoopbackAddress()) {
                            if (address instanceof Inet6Address) {
                                ipv6Result.add(normalizeHostAddress(address));
                            } else {
                                ipv4Result.add(normalizeHostAddress(address));
                            }
                        }
                    }
                }
                System.out.println(ipv4Result);
                String ip;
                String var14;
                if (!ipv4Result.isEmpty()) {
                    Iterator i$ = ipv4Result.iterator();

                    do {
                        if (!i$.hasNext()) {
                            var14 = (String)ipv4Result.get(ipv4Result.size() - 1);
                            return var14;
                        }

                        ip = (String)i$.next();
                    } while(ip.startsWith("127.0") || ip.startsWith("192.168"));

                    return ip;
                }

                if (!ipv6Result.isEmpty()) {
                    var14 = (String)ipv6Result.get(0);
                    return var14;
                }

                InetAddress localHost = InetAddress.getLocalHost();
                ip = normalizeHostAddress(localHost);
                return ip;
            } catch (SocketException var10) {
                var10.printStackTrace();
            } catch (UnknownHostException var11) {
                var11.printStackTrace();
            }

            return null;
        } finally {
            ;
        }
    }

    public static String normalizeHostAddress(InetAddress localHost) {
        return localHost instanceof Inet6Address ? "[" + localHost.getHostAddress() + "]" : localHost.getHostAddress();
    }

    public static void main(String[] args) {
        System.out.println(getLocalAddress());
        System.out.println(getIP());
    }

}
