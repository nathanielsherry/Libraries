package fava;



import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import fava.datatypes.Pair;
import fava.functionable.FList;
import fava.signatures.FnCombine;
import fava.signatures.FnFold;
import fava.signatures.FnEach;
import fava.signatures.FnMap;
import fava.signatures.FnMap2;


public class Functions
{

	
	public static FnFold<String, String> strcat()
	{
		return new FnFold<String, String>() {

			public String f(String s1, String s2)
			{
				return s1.toString() + s2;
			}
		};
	}

	public static <T1> FnFold<T1, String> strcatObj()
	{
		return new FnFold<T1, String>() {

			public String f(T1 s1, String s2)
			{
				return s1.toString() + s2;
			}
		};
	}

	public static FnFold<String, String> strcat(final String separator)
	{
		return new FnFold<String, String>() {

			public String f(String s1, String s2)
			{
				return s1 + separator + s2;
			}
		};
	}
	
	public static <T1> FnFold<List<T1>, List<T1>> listConcat()
	{

		return new FnFold<List<T1>, List<T1>>() {

			public List<T1> f(List<T1> l1, List<T1> l2)
			{
				l1.addAll(l2);
				return l1;
			}

		};

	}
	
	
	public static <T1> FnFold<FList<T1>, FList<T1>> flistConcat()
	{

		return new FnFold<FList<T1>, FList<T1>>() {

			public FList<T1> f(FList<T1> l1, FList<T1> l2)
			{
				l1.addAll(l2);
				return l1;
			}

		};

	}

	
	public static <T1> FnMap<T1, Boolean> equiv(final T1 item)
	{
		return new FnMap<T1, Boolean>() {

			public Boolean f(T1 s1)
			{
				return item.equals(s1);
			}
		};
	}
	
	public static <T1> FnCombine<T1, Boolean> equiv()
	{
		return new FnCombine<T1, Boolean>() {

			public Boolean f(T1 o1, T1 o2)
			{
				return o1.equals(o2);
			}
		};
	}

	public static <T1> FnMap<T1, Boolean> notEquiv(final T1 item)
	{
		return compose(not(), equiv(item));
	}
	
	public static <T1> FnCombine<T1, Boolean> notEquiv()
	{
		return compose(not(), Functions.<T1>equiv());
	}
	
	

	public static <T1, T2> FnMap<Pair<T1, T2>, T1> first()
	{
		return new FnMap<Pair<T1, T2>, T1>() {

			public T1 f(Pair<T1, T2> element)
			{
				return element.first;
			}
		};
	}

	public static <T1, T2> FnMap<Pair<T1, T2>, T2> second()
	{
		return new FnMap<Pair<T1, T2>, T2>() {

			public T2 f(Pair<T1, T2> element)
			{
				return element.second;
			}
		};
	}

	

	public static <T1> FnMap<T1, Boolean> bTrue()
	{
		return new FnMap<T1, Boolean>() {

			public Boolean f(T1 element)
			{
				return true;
			}

		};
	}

	public static <T1> FnMap<T1, Boolean> bFalse()
	{
		return new FnMap<T1, Boolean>() {

			public Boolean f(T1 element)
			{
				return false;
			}

		};
	}

	

	public static <T1> FnMap<T1, T1> id()
	{
		return new FnMap<T1, T1>() {

			public T1 f(T1 element)
			{
				return element;
			}

		};
	}

	

	public static <T1> FnMap<T1, Boolean> notNull()
	{
		return new FnMap<T1, Boolean>() {

			public Boolean f(T1 element)
			{
				return element != null;
			}

		};
	}


	
	public static FnFold<Boolean, Boolean> and()
	{
		return new FnFold<Boolean, Boolean>() {

			public Boolean f(Boolean b1, Boolean b2)
			{
				return b1 && b2;
			}
		};
	}

	public static FnFold<Boolean, Boolean> or()
	{
		return new FnFold<Boolean, Boolean>() {

			public Boolean f(Boolean b1, Boolean b2)
			{
				return b1 || b2;
			}
		};
	}

	public static FnMap<Boolean, Boolean> not()
	{
		return new FnMap<Boolean, Boolean>() {


			public Boolean f(Boolean element) {
				return ! element;
			}
		};
	}

	

	public static <T1> FnEach<T1> print()
	{
		return new FnEach<T1>() {

			public void f(T1 element)
			{
				System.out.println(element.toString());
			}
		};
	}

	public static <T1> FnMap<T1, String> show()
	{
		return new FnMap<T1, String>(){

			public String f(T1 element) {
				return element.toString();
			}};
	}
	
	
	
	public static <T1, T2, T3> FnMap<T1, T3> compose(final FnMap<T2, T3> h, final FnMap<T1, T2> g)
	{
		return new FnMap<T1, T3>(){

			public T3 f(T1 element) {
				return h.f(g.f(element));
			}};
	}
	
	public static <T1, T2, T3> FnCombine<T1, T3> compose(final FnMap<T2, T3> h, final FnCombine<T1, T2> g)
	{
		return new FnCombine<T1, T3>() {

			public T3 f(T1 v1, T1 v2) {
				return h.f(g.f(v1, v2));
			}

		};
	}
	
	public static <T1a, T1b, T2, T3> FnMap2<T1a, T1b, T3> compose(final FnMap<T2, T3> h, final FnMap2<T1a, T1b, T2> g)
	{
		return new FnMap2<T1a, T1b, T3>() {

			public T3 f(T1a v1, T1b v2) {
				return h.f(g.f(v1, v2));
			}
		};
	}
	
	
	public static FnFold<Integer, Integer>addi()
	{
		return new FnFold<Integer, Integer>() {

			public Integer f(Integer v1, Integer v2) {
				return v1 + v2;
			}
		};
	}
	public static FnFold<Long, Long>addl()
	{
		return new FnFold<Long, Long>() {

			public Long f(Long v1, Long v2) {
				return v1 + v2;
			}
		};
	}
	public static FnFold<Short, Short>adds()
	{
		return new FnFold<Short, Short>() {

			public Short f(Short v1, Short v2) {
				return (short)(v1 + v2);
			}
		};
	}
	public static FnFold<Float, Float>addf()
	{
		return new FnFold<Float, Float>() {

			public Float f(Float v1, Float v2) {
				return v1 + v2;
			}
		};
	}
	public static FnFold<Double, Double>addd()
	{
		return new FnFold<Double, Double>() {

			public Double f(Double v1, Double v2) {
				return v1 + v2;
			}
		};
	}
	public static FnFold<Byte, Byte>addb()
	{
		return new FnFold<Byte, Byte>() {

			public Byte f(Byte v1, Byte v2) {
				return (byte)(v1 + v2);
			}
		};
	}
	public static FnFold<BigInteger, BigInteger>addbi()
	{
		return new FnFold<BigInteger, BigInteger>() {

			public BigInteger f(BigInteger v1, BigInteger v2) {
				return v1.add(v2);
			}
		};
	}
	public static FnFold<BigDecimal, BigDecimal>addbd()
	{
		return new FnFold<BigDecimal, BigDecimal>() {

			public BigDecimal f(BigDecimal v1, BigDecimal v2) {
				return v1.add(v2);
			}
		};
	}

	
	public static <T1 extends Number> FnMap<T1, Boolean> lt(final Number compare)
	{
		return new FnMap<T1, Boolean>(){

			public Boolean f(T1 num) {
				return (num.doubleValue() < compare.doubleValue());
			}};
	}
	
	public static <T1 extends Number> FnMap<T1, Boolean> gt(final Number compare)
	{
		return new FnMap<T1, Boolean>(){

			public Boolean f(T1 num) {
				return (num.doubleValue() > compare.doubleValue());
			}};
	}
	
}
