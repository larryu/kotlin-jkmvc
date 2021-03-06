package com.jkmvc.http

import java.util.concurrent.ConcurrentHashMap

/**
 * 视图
 *
 * @author shijianhang
 * @date 2016-10-21 下午3:14:54  
 */
class View(override val req: Request /* 请求对象 */, override val res: Response /* 响应对象 */, override val file:String/* 视图文件 */, override var data:MutableMap<String, Any?> /* 局部变量 */):IView
{
	companion object{
		/**
		 * 全局变量
		 */
		protected val globalData:ConcurrentHashMap<String, Any?> = ConcurrentHashMap<String, Any?>();

		/**
		 * 设置全局变量
		 * @param key
		 * @param value
		 * @return
		 */
		public fun setGlobal(key:String, value:Any?): Companion {
			globalData.set(key, value);
			return this;
		}
	}

	/**
	 * 设置局部变量
	 * @param key
	 * @param value
	 * @return
	 */
	public override operator fun set(key:String, value:Any?): View {
		this.data[key] = value;
		return this;
	}

	/**
	 * 渲染视图
	 */
	public override fun render(){
 		// 设置全局变量
		req.setAttributes(globalData)

		// 设置局部变量
		req.setAttributes(data)

		// 渲染jsp
		req.getRequestDispatcher("/" + file + ".jsp").forward(req, res)
	}

}