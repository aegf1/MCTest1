package network;

import java.text.DecimalFormat;
import java.util.UUID;

import com.josephb.test1.utility.physics.Vector3;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class ParticleDataMessage implements IMessage
{
	Vector3 position;
	Vector3 momentum;
	UUID particleID;		//	16 characters long
	String data;
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		data = ByteBufUtils.readUTF8String(buf);
		
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
//		data = particleID.
		ByteBufUtils.writeUTF8String(buf, data);
	}
	
	/**
	 * Creates a string with the rounded values of the Vector3:
	 * xxxxx.xxxx:yyyyy.yyyy:zzzzz.zzzz:
	 * e.g. (123.7657675, 17333.12, 1.12) 
	 * -> "00123.7658:17333.1200:00001.1200:"
	 * total: 33 characters
	 * @param vec the vector
	 * @return the string
	 */
	private String vector3ToTruncatedString(Vector3 vec)
	{
		DecimalFormat df = new DecimalFormat("#####.####");
		String x = df.format(vec.getX());
		String y = df.format(vec.getY());
		String z = df.format(vec.getZ());
		return x+":"+y+":"+z+":";
	}

}
