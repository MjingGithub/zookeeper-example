package org.mj.study.zookeeper;

import java.io.IOException;
import java.net.InetAddress;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class SimpleZookeeperExample {
	// 根节点
	public static final String ROOT = "/root-zoo";
	
	public void tryZookeeperBasicOper() throws IOException, KeeperException, InterruptedException{
		//创建一个与服务器的连接
		ZooKeeper zk = new ZooKeeper("localhost:2181", 300000, new Watcher(){
			//监控所有被触发的事件
			public void process(WatchedEvent event) {
				System.out.println("触发了-"+event.getType()+"事件!");
				
			}
			
		});
		
		zk.create(ROOT, "root-zoo-data".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT) ;
		zk.create(ROOT+"/zoo1","zoo1-data".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println(zk.getChildren(ROOT,true)) ;
		System.out.println(new String(zk.getData(ROOT+"/zoo1",false,null))) ;
		//修改子目录节点数据
		zk.setData(ROOT+"/zoo1", "modify-zoo1-data".getBytes(), -1) ;
		//目录节点状态
		System.out.println("目录节点状态：["+zk.exists("/testRootPath",true)+"]"); 
		//创建另外一个目录节点
		zk.create(ROOT+"/zoo2", "zoo2-data".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT) ;
		System.out.println(zk.getData(ROOT+"zoo2", true, null)); 
		//删除子目录节点
		zk.delete(ROOT+"/zoo1", -1);
		zk.delete(ROOT+"/zoo2", -1);
		//删除父目录节点
		zk.delete("/root-zoo", -1);
		//关闭连接
		zk.close();
	}
	
	public static void main(String[] args){
	    SimpleZookeeperExample  sze= new SimpleZookeeperExample() ;
	    try {
			sze.tryZookeeperBasicOper() ;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
