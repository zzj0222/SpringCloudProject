package com.demo.utils.common;

import java.util.List;


 import org.apache.curator.RetryPolicy;
 import org.apache.curator.framework.CuratorFramework;
 import org.apache.curator.framework.CuratorFrameworkFactory;
 import org.apache.curator.retry.ExponentialBackoffRetry;
 import org.apache.zookeeper.CreateMode;
 import org.apache.zookeeper.ZooDefs;
 import org.apache.zookeeper.data.Stat;

 import java.util.List;

 /**
 * zookeeper 操作工具类
 * @author lenovo
 *
 */
public class ZookeeperUtil {

    // Curator客户端
    public static CuratorFramework client = null;
    // 集群模式则是多个ip
//   private static final String zkServerIps = "192.168.190.128:2181,192.168.190.129:2181,192.168.190.130:2181";

    private static final String zkServerIps = "localhost:2181";

    private static void connect(){

        // 重连策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 5);

        // 实例化Curator客户端
        client = CuratorFrameworkFactory.builder() // 使用工厂类来建造客户端的实例对象
                .connectString(zkServerIps)  // 放入zookeeper服务器ip
                .sessionTimeoutMs(10000).retryPolicy(retryPolicy)  // 设定会话时间以及重连策略
                .namespace("workspace").build();  // 设置命名空间以及开始建立连接

        // 启动Curator客户端
        client.start();
    }

    // 关闭zk客户端连接
    private static void closeZKClient() {
        if (client != null) {
            client.close();
        }
    }

    /**
     * 创建父节点
     * @param nodePath 节点路径
     * @param nodeData 节点数据
     */
    public static void createParentNode(String nodePath,String nodeData){
        // 1、创建父节点
        try {
            // 2、连接
            connect();

            String result = client.create().creatingParentsIfNeeded()  // 创建父节点，也就是会递归创建
                    .withMode(CreateMode.PERSISTENT)  // 节点类型
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)  // 节点的acl权限
                    .forPath(nodePath, nodeData.getBytes());

            System.out.println(result + "节点，创建成功...");

            // 关闭
            closeZKClient();
        } catch (Exception e) {
            System.out.println("节点不存在:"+e.toString());
        }
    }

    /**
     * 更新节点
     * @param nodePath 节点路径
     * @param newNodeData 新节点数据
     */
    public static void updateNode(String nodePath,String newNodeData){
        // 1、更新节点
        try {

            // 2、连接
            connect();

            Stat resultStat = client.setData().withVersion(0) // 指定数据版本
                    .forPath(nodePath, newNodeData.getBytes());// 需要修改的节点路径以及新数据

            System.out.println("更新节点数据成功，新的数据版本为：" + resultStat.getVersion());

            // 关闭
            closeZKClient();
        } catch (Exception e) {
            System.out.println("节点不存在:"+e.toString());
        }

    }

    /**
     * 删除节点
     * @param nodePath 节点路径
     */
    public static void deleteNode(String nodePath){
        try {
            // 2、连接
            connect();
            Stat stat = new Stat();
            client.delete()
                    .guaranteed()  // 如果删除失败，那么在后端还是会继续删除，直到成功
                    .deletingChildrenIfNeeded()  // 子节点也一并删除，也就是会递归删除
                    .withVersion(stat.getVersion())
                    .forPath(nodePath);

            // 关闭
            closeZKClient();
        } catch (Exception e) {
            System.out.println("节点不存在:"+e.toString());
        }
    }

    /**
     * 节点查询
     * @param nodePath 节点路径
     * @return
     */
    public static String selectNode(String nodePath){
        // 读取节点数据
        String nodeData = "";
        try {
            // 2、连接
            connect();

            Stat stat = new Stat();
            byte[] nodeByte = client.getData().storingStatIn(stat).forPath(nodePath);
            nodeData = new String(nodeByte);
            System.out.println("节点 " + nodePath + " 的数据为：" + nodeData);
            System.out.println("该节点的数据版本号为：" + stat.getVersion());

            // 关闭
            closeZKClient();
        } catch (Exception e) {
            System.out.println("节点不存在:"+e.toString());
        }

        return nodeData;

    }

    /**
     * 获取子节点
     * @param nodePath
     * @return
     */
    public static List<String> selectChildrenNode(String nodePath){
        List<String> childNodes = null;
        try {
            // 2、连接
            connect();

            childNodes = client.getChildren().forPath(nodePath);
            System.out.println(nodePath + " 节点下的子节点列表：");
            for (String childNode : childNodes) {
                System.out.println(childNode);
            }

            // 关闭
            closeZKClient();
        } catch (Exception e) {
            System.out.println("子节点不存在:"+e.toString());
        }

        return childNodes;

    }

    /**
     * 判断节点是否存在
     * @param nodePath
     * @return
     */
    public static boolean containsNode(String nodePath){
        // 1、节点boolean值
        boolean nodeFlag = false;
        try {
            // 2、连接
            connect();

            Stat statExist = client.checkExists().forPath(nodePath);

            if (statExist == null) {
                System.out.println(nodePath + " 节点不存在");
                nodeFlag = false;

            } else {
                System.out.println(nodePath + " 节点存在");
                nodeFlag = true;
            }

            // 关闭
            closeZKClient();
        } catch (Exception e) {
            System.out.println("节点不存在:"+e.toString());
        }
        return nodeFlag;

    }


    public static void main(String[] args) throws InterruptedException {
//    	ZookeeperUtil.createParentNode("/tony4/tony2", "tony");

//    	String tony = ZookeeperUtil.selectNode("/tony");
//    	System.out.println("tonydata:" + tony);

        // 修改节点
//    	ZookeeperUtil.updateNode("/tony2", "222");

//    	String ton1y = ZookeeperUtil.selectNode("/tony2");
//    	System.out.println("tonydata:" + ton1y);


//    	ZookeeperUtil.deleteNode("/tony");

//    	String ton1y = ZookeeperUtil.selectNode("/tony");
//    	System.out.println("tonydata:" + ton1y);

        ZookeeperUtil.selectChildrenNode("/tony4");
    }

}
