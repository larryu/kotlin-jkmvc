package com.jkmvc.common

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.javaType
import kotlin.reflect.memberFunctions
import kotlin.reflect.staticFunctions

/****************************** 反射扩展 *******************************/
/**
 * 匹配方法的名称与参数类型
 */
public fun KFunction<*>.matches(name:String, paramTypes:List<Class<*>>? = null):Boolean{
    // 1 匹配名称
    if(name != this.name)
        return false

    // 2 匹配参数
    // 2.1 匹配参数个数
    var size = 0;
    if(paramTypes != null)
        size = paramTypes.size
    if(size != this.parameters.size)
        return false;

    // 2.2 匹配参数类型
    if(paramTypes != null){
        for (i in paramTypes.indices){
            var targetType = this.parameters[i].type.javaType;
            if(targetType is ParameterizedTypeImpl) // 若是泛型类型，则去掉泛型，只保留原始类型
                targetType = targetType.rawType;

            if(paramTypes[i] != targetType)
                return false
        }
    }

    return true;
}

/**
 * 查找方法
 */
public fun KClass<*>.findFunction(name:String, paramTypes:MutableList<Class<*>> = mutableListOf()): KFunction<*>?{
    paramTypes.add(0, this.java); // 第一个参数为this
    return memberFunctions.find {
        it.matches(name, paramTypes);
    }
}

/**
 * 查找静态方法
 */
public fun KClass<*>.findStaticFunction(name:String, paramTypes:List<Class<*>> = mutableListOf()): KFunction<*>?{
    return staticFunctions.find {
        it.matches(name, paramTypes);
    }
}

/**
 * 查找构造函数
 */
public fun KClass<*>.findConstructor(paramTypes:List<Class<*>>? = null): KFunction<*>?{
    return constructors.find {
        it.matches("<init>", paramTypes); // 构造函数的名称为 <init>
    }
}