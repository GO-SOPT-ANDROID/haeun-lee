package org.android.go.sopt

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import timber.log.Timber

class GoSoptApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initTimber()
        initSharedPreferences()
        initMockJsonString()
    }

    private fun initMockJsonString() {
        mockJsonString = kotlin.runCatching {
            loadAsset(FILE_MOCK_REPO_LIST)
        }.getOrNull()
    }

    private fun loadAsset(fileName: String): String {
        return assets.open(fileName).use { inputStream ->
            val size = inputStream.available()
            val bytes = ByteArray(size)
            inputStream.read(bytes)
            String(bytes)
        }
    }

    private fun initSharedPreferences() {
        // AES256_GCM 암호화 알고리즘으로 마스터 키 생성
        val masterKeyAlias = MasterKey
            .Builder(applicationContext, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        // sharedPreferences 인스턴스 초기화
        prefs = EncryptedSharedPreferences.create(
            applicationContext,
            applicationContext.packageName,
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    // 클래스가 처음 로딩될 때 초기화 되는 companion object
    // 프로그램과 생명주기를 같이 하며, 한번만 생성된다. (싱글톤)
    companion object {
        lateinit var prefs: SharedPreferences
        var mockJsonString: String? = null

        private const val FILE_MOCK_REPO_LIST = "mock_repo_list.json"
    }
}