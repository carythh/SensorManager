/* **************************************************
 Copyright (c) 2012, University of Cambridge
 Neal Lathia, neal.lathia@cl.cam.ac.uk
 Kiran Rachuri, kiran.rachuri@cl.cam.ac.uk

This library was developed as part of the EPSRC Ubhave (Ubiquitous and
Social Computing for Positive Behaviour Change) Project. For more
information, please visit http://www.emotionsense.org

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 ************************************************** */

package com.ubhave.sensormanager.sensors.env;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.process.push.LightProcessor;
import com.ubhave.sensormanager.sensors.SensorUtils;

public class LightSensor extends AbstractEnvironmentSensor
{
	private static final String TAG = "LightSensor";
	private static LightSensor sensor;

	public static LightSensor getSensor(final Context context)
	{
		if (sensor == null)
		{
			synchronized (lock)
			{
				if (sensor == null)
				{
					sensor = new LightSensor(context);
				}
			}
		}
		return sensor;
	}

	private LightSensor(final Context context)
	{
		super(context);
	}

	public String getLogTag()
	{
		return TAG;
	}

	public int getSensorType()
	{
		return SensorUtils.SENSOR_TYPE_LIGHT;
	}

	@Override
	protected Sensor getSensor()
	{
		return sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
	}

	@Override
	protected SensorData processEvent(SensorEvent event)
	{
		float light = event.values[0];
		float maxRange = event.sensor.getMaximumRange();
		
		LightProcessor processor = (LightProcessor) getProcessor();
		return processor.process(System.currentTimeMillis(), sensorConfig.clone(), light, maxRange);
	}
}
