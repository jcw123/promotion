package com.im.sky.netty.test.test2.util;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * @author jiangchangwei
 * @date 2020-6-23 下午 4:24
 **/
public class NetUtils {

    public static String toIpString(InetSocketAddress address) {
        if (address == null) {
            return null;
        } else {
            InetAddress inetAddress = address.getAddress();
            return inetAddress == null ? address.getHostName() :
                    inetAddress.getHostAddress();
        }
    }

    public static String channelToString(SocketAddress local1, SocketAddress remote1) {
        try {
            InetSocketAddress local = (InetSocketAddress) local1;
            InetSocketAddress remote = (InetSocketAddress) remote1;
            return toAddressString(local) + " -> " + toAddressString(remote);
        } catch (Exception e) {
            return local1 + "->" + remote1;
        }
    }

    public static String toAddressString(InetSocketAddress address) {
        if (address == null) {
            return "";
        } else {
            return toIpString(address) + ":" + address.getPort();
        }
    }
}
