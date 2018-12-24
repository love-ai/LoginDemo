package com.ltz.mymvp.network;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 兼容null字段
 * Created by kyle on 2017/3/24.
 */

class CIGsonConverterFactory extends Converter.Factory {

    static CIGsonConverterFactory create() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return create(gson);
    }

    @SuppressWarnings("WeakerAccess")
    static CIGsonConverterFactory create(Gson gson) {
        return new CIGsonConverterFactory(gson);
    }

    private Gson gson;

    private CIGsonConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        if (type instanceof ParameterizedType) {
            Class<?> c = (Class<?>) ((ParameterizedType) type).getRawType();
            if (!HttpResponse.class.isAssignableFrom(c)) {
                return null;
            }
        } else {
            return null;
        }
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CIGsonResponseBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CIGsonRequestBodyConverter<>(gson, adapter);
    }

    @SuppressWarnings("WeakerAccess")
    public final static class CIGsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private static final Charset UTF_8 = Charset.forName("UTF-8");

        private final Gson gson;
        private final TypeAdapter<T> adapter;

        CIGsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public RequestBody convert(T value) throws IOException {
            Buffer buffer = new Buffer();
            Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
            JsonWriter jsonWriter = gson.newJsonWriter(writer);
            adapter.write(jsonWriter, value);
            jsonWriter.close();
            return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
        }
    }

    @SuppressWarnings("WeakerAccess")
    public final class CIGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final Gson gson;
        private final TypeAdapter<T> adapter;

        CIGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            String json = value.string();
            if(json.contains(",\"data\":true")){
                json=json.replaceAll("true","{}");
            }
            LogUtils.d(json);
            T ret;
            try {
                ret = adapter.fromJson(json);
            }finally {
                value.close();
            }
            if (ret != null && ret instanceof HttpResponse && ((HttpResponse) ret).getData() == null) {
                if (((HttpResponse) ret).getCode() != 1) {
                    return ret;
                }
                //noinspection unchecked
                ((HttpResponse) ret).setData(new Object());
                json = gson.toJson(ret);
                LogUtils.json(json);
                return adapter.fromJson(json);
            } else {
                return ret;
            }
        }
    }
}
