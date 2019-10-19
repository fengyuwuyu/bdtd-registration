package com.bdtd.card.registration.cache;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.stylefeng.guns.modular.system.model.Tree;
import com.stylefeng.guns.scmmain.dao.IDtDepDao;

/**
 * 用于缓存部门序号，防止多次递归查询部门及其子部门序号，init方法在应用程序启动时调用一次，之后每次同步部门数据后调用一次
 * @author ll
 *
 */
public class DepCache {

	public static Tree root;
	private static boolean init = false;
	
	public static List<Tree> select(Long id){
	    if (id == null) {
	        return Arrays.asList(root);
	    }
	    
	    Tree r = findByDepSerial(root, id);
	    return Arrays.asList(r);
	}
	
	public static void init(IDtDepDao depMapper){
		if (!init) {
			Long depSerial = 10000L;
			root = new Tree(depSerial,depMapper);
			init = true;
		}
	}
	
	public static void refresh(IDtDepDao depMapper){
		Long depSerial = 10000L;
		Tree root = new Tree(depSerial,depMapper);
		DepCache.root = root;
	}
	
	/**
	 * 根据部门编号查找其所有下级公司及部门（包括自己）
	 * @param depSerial
	 * @return
	 */
	public static Set<Long> getChildren(Long depSerial){
		Set<Long> result = new HashSet<Long>();
		Tree depTree = findByDepSerial(root,depSerial);
		if(depTree!=null){
			depTree.getAllChildren(result);
		}
		return result;
	}
	
	/**
	 * 根据部门编号集合查找其所有下级公司及部门（包括自己）
	 * @param depSerials
	 * @return
	 */
	public static Set<Long> getChildren(List<Long> depSerials){
		Set<Long> result = new HashSet<Long>();
		if(depSerials==null||depSerials.size()==0){
			return null;
		}
		for(Long depSerial : depSerials){
			result.addAll(getChildren(depSerial));
		}
		return result;
	}
	
	/**
	 * 根据部门编号集合查找其所有下级公司及部门（包括自己）
	 * @param depSerials
	 * @return
	 */
	public static Set<Long> getChildren(Long[] depSerials){
		Set<Long> result = new HashSet<Long>();
		if(depSerials == null||depSerials.length == 0){
			return null;
		}
		for(Long depSerial : depSerials){
			result.addAll(getChildren(depSerial));
		}
		return result;
	}
	
	public static Tree findByDepSerial(Tree depTree,Long depSerial){
		if(depTree.getId().longValue()==depSerial.longValue()){
			return depTree;
		}else{
			if(depTree.getChildren()!=null&&depTree.getChildren().size()>0){
				for(Tree tree : depTree.getChildren()){
					Tree result = findByDepSerial(tree,depSerial);
					if(result!=null){
						return result;
					}
				}
			}
		}
		return null;
	}
	
}
