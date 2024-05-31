package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.zumzeig.R

class InformacioStatic : Fragment() {
    // Inflate the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_informacio_static, container, false)
        // Setup "What we do" section
        setupSection1(
            view,
            R.id.weDo,
            R.id.weDoText,
            R.id.descriptionText,
            R.id.btnVeureMes,
            R.id.moreText,
            R.id.ImgWeDo,
            R.id.moreText2,
            R.id.btnVeureMenys,
            imageUrl = "https://enricsanchezmontoya.cat/zumzeig/images/Static/queFemInformacioStatic.jpg"
        )

        // Setup "History" section
        setupSection5(
            view,
            R.id.history,
            R.id.historyText,
            R.id.descriptionHisText,
            R.id.btnVeureMesHis,
            R.id.ImgHistory,
            R.id.moreTextHis,
            R.id.moreText2His,
            R.id.btnVeureMenysHis,
            imageUrl = "https://enricsanchezmontoya.cat/zumzeig/images/Static/historiaInformacioStatic.jpg"
        )

        // Setup "Who we are" section
        setupSection2(
            view,
            R.id.whoWeAre,
            R.id.whoWeAreText,
            R.id.descriptionWhoText,
            R.id.btnVeureMesWho,
            R.id.titleMoreTextWho1,
            R.id.moreText1Who1,
            R.id.moreText2Who1,
            R.id.moreText3Who1,
            R.id.titleMoreTextWho2,
            R.id.moreText1Who2,
            R.id.titleMoreTextWho3,
            R.id.moreText1Who3,
            R.id.moreText2Who3,
            R.id.btnVeureMenysWho
        )

        // Setup "Cooperativism" section
        setupSection3(
            view,
            R.id.cooperativism,
            R.id.cooperativismText,
            R.id.descriptionTextCoop,
            R.id.btnVeureMesCoop,
            R.id.moreTextCopp,
            R.id.moreText2Copp,
            R.id.btnVeureMenysCopp
        )

        // Setup "Neighborhood" section
        setupSection1(
            view,
            R.id.neighborhood,
            R.id.neighborhoodText,
            R.id.descriptionTextNeig,
            R.id.btnVeureMesNeig,
            R.id.moreTextNeig,
            R.id.ImgNeighborhood,
            R.id.moreText2Neig,
            R.id.btnVeureMenysNeig,
            imageUrl = "https://enricsanchezmontoya.cat/zumzeig/images/Static/barrioInformacioStatic.jpg"
        )

        // Setup "Educational Activities" section
        setupSection4(
            view,
            R.id.eduActivities,
            R.id.eduActivitiesText,
            R.id.descriptionTextEduAc,
            R.id.btnVeureMesEduAc,
            R.id.ImgEduActivities,
            R.id.moreTextEduAc,
            R.id.btnVeureMenysEduAc,
            imageUrl = "https://enricsanchezmontoya.cat/zumzeig/images/Static/actividadesInformacioStatic.jpg"
        )

        return view
    }
    // Setup methods for each section with expandable text
    private fun setupSection1(
        view: View,
        layoutId: Int,
        titleId: Int,
        descriptionId: Int,
        btnVeureMesId: Int,
        moreTextId: Int,
        imageViewId: Int = View.NO_ID,  // Valor por defecto
        moreText2Id: Int = View.NO_ID,  // Valor por defecto
        btnVeureMenysId: Int = View.NO_ID,  // Valor por defecto
        imageUrl: String? = null  // Valor por defecto
    ) {
        // Initialization of views
        val titleText = view.findViewById<TextView>(titleId)
        val descriptionText = view.findViewById<TextView>(descriptionId)
        val btnVeureMes = view.findViewById<TextView>(btnVeureMesId)
        val moreText = view.findViewById<TextView>(moreTextId)
        val imageView = if (imageViewId != View.NO_ID) view.findViewById<ImageView>(imageViewId) else null
        val moreText2 = if (moreText2Id != View.NO_ID) view.findViewById<TextView>(moreText2Id) else null
        val btnVeureMenys = if (btnVeureMenysId != View.NO_ID) view.findViewById<TextView>(btnVeureMenysId) else null
        // Click listeners to expand/collapse text
        btnVeureMes.setOnClickListener {
            moreText.visibility = View.VISIBLE
            imageView?.visibility = View.VISIBLE
            moreText2?.visibility = View.VISIBLE
            btnVeureMenys?.visibility = View.VISIBLE
            btnVeureMes.visibility = View.GONE
            imageUrl?.let { url ->
                imageView?.let { iv -> Glide.with(view).load(url).into(iv) }
            }
        }

        btnVeureMenys?.setOnClickListener {
            moreText.visibility = View.GONE
            imageView?.visibility = View.GONE
            moreText2?.visibility = View.GONE
            btnVeureMenys.visibility = View.GONE
            btnVeureMes.visibility = View.VISIBLE
        }
    }

    private fun setupSection2(
        view: View,
        layoutId: Int,
        titleId: Int,
        descriptionId: Int,
        btnVeureMesId: Int,
        title1Id: Int,
        moreText11Id: Int,
        moreText21Id: Int,
        moreText31Id: Int,
        title2Id: Int,
        moreText12Id: Int,
        title3Id: Int,
        moreText13Id: Int,
        moreText23Id: Int,
        btnVeureMenysId: Int = View.NO_ID  // Valor por defecto
    ) {
        // Initialization of views
        val titleText = view.findViewById<TextView>(titleId)
        val descriptionText = view.findViewById<TextView>(descriptionId)
        val btnVeureMes = view.findViewById<TextView>(btnVeureMesId)
        val title1Text = view.findViewById<TextView>(title1Id)
        val moreText11 = view.findViewById<TextView>(moreText11Id)
        val moreText21 = view.findViewById<TextView>(moreText21Id)
        val moreText31 = view.findViewById<TextView>(moreText31Id)
        val title2Text = view.findViewById<TextView>(title2Id)
        val moreText12 = view.findViewById<TextView>(moreText12Id)
        val title3Text = view.findViewById<TextView>(title3Id)
        val moreText13 = view.findViewById<TextView>(moreText13Id)
        val moreText23 = view.findViewById<TextView>(moreText23Id)
        val btnVeureMenys = if (btnVeureMenysId != View.NO_ID) view.findViewById<TextView>(btnVeureMenysId) else null
// Click listeners to expand/collapse text
        btnVeureMes.setOnClickListener {
            moreText11.visibility = View.VISIBLE
            moreText21.visibility = View.VISIBLE
            moreText31.visibility = View.VISIBLE
            moreText12.visibility = View.VISIBLE
            moreText13.visibility = View.VISIBLE
            moreText23.visibility = View.VISIBLE
            title1Text.visibility = View.VISIBLE
            title2Text.visibility = View.VISIBLE
            title3Text.visibility = View.VISIBLE
            btnVeureMenys?.visibility = View.VISIBLE
            btnVeureMes.visibility = View.GONE
        }

        btnVeureMenys?.setOnClickListener {
            moreText11.visibility = View.GONE
            moreText21.visibility = View.GONE
            moreText31.visibility = View.GONE
            moreText12.visibility = View.GONE
            moreText13.visibility = View.GONE
            moreText23.visibility = View.GONE
            title1Text.visibility = View.GONE
            title2Text.visibility = View.GONE
            title3Text.visibility = View.GONE
            btnVeureMenys.visibility = View.GONE
            btnVeureMes.visibility = View.VISIBLE
        }
    }

    private fun setupSection3(
        view: View,
        layoutId: Int,
        titleId: Int,
        descriptionId: Int,
        btnVeureMesId: Int,
        moreTextId: Int,
        moreText2Id: Int = View.NO_ID,  // Valor por defecto
        btnVeureMenysId: Int = View.NO_ID  // Valor por defecto
    ) {
        // Initialization of views
        val titleText = view.findViewById<TextView>(titleId)
        val descriptionText = view.findViewById<TextView>(descriptionId)
        val btnVeureMes = view.findViewById<TextView>(btnVeureMesId)
        val moreText = view.findViewById<TextView>(moreTextId)
        val moreText2 = if (moreText2Id != View.NO_ID) view.findViewById<TextView>(moreText2Id) else null
        val btnVeureMenys = if (btnVeureMenysId != View.NO_ID) view.findViewById<TextView>(btnVeureMenysId) else null
// Click listeners to expand/collapse text
        btnVeureMes.setOnClickListener {
            moreText.visibility = View.VISIBLE
            moreText2?.visibility = View.VISIBLE
            btnVeureMenys?.visibility = View.VISIBLE
            btnVeureMes.visibility = View.GONE
        }

        btnVeureMenys?.setOnClickListener {
            moreText.visibility = View.GONE
            moreText2?.visibility = View.GONE
            btnVeureMenys.visibility = View.GONE
            btnVeureMes.visibility = View.VISIBLE
        }
    }

    private fun setupSection4(
        view: View,
        layoutId: Int,
        titleId: Int,
        descriptionId: Int,
        btnVeureMesId: Int,
        imageViewId: Int = View.NO_ID,  // Valor por defecto
        moreTextId: Int,
        btnVeureMenysId: Int = View.NO_ID,  // Valor por defecto
        imageUrl: String? = null  // Valor por defecto
    ) {
        // Initialization of views
        val titleText = view.findViewById<TextView>(titleId)
        val descriptionText = view.findViewById<TextView>(descriptionId)
        val btnVeureMes = view.findViewById<TextView>(btnVeureMesId)
        val imageView = if (imageViewId != View.NO_ID) view.findViewById<ImageView>(imageViewId) else null
        val moreText = view.findViewById<TextView>(moreTextId)
        val btnVeureMenys = if (btnVeureMenysId != View.NO_ID) view.findViewById<TextView>(btnVeureMenysId) else null
        // Click listeners to expand/collapse text
        btnVeureMes.setOnClickListener {
            moreText.visibility = View.VISIBLE
            imageView?.visibility = View.VISIBLE
            btnVeureMenys?.visibility = View.VISIBLE
            btnVeureMes.visibility = View.GONE
            imageUrl?.let { url ->
                imageView?.let { iv -> Glide.with(view).load(url).into(iv) }
            }
        }

        btnVeureMenys?.setOnClickListener {
            moreText.visibility = View.GONE
            imageView?.visibility = View.GONE
            btnVeureMenys.visibility = View.GONE
            btnVeureMes.visibility = View.VISIBLE
        }
    }
    private fun setupSection5(
        view: View,
        layoutId: Int,
        titleId: Int,
        descriptionId: Int,
        btnVeureMesId: Int,
        imageViewId: Int = View.NO_ID,  // Valor por defecto
        moreTextId: Int,
        moreText2Id: Int = View.NO_ID,  // Valor por defecto
        btnVeureMenysId: Int = View.NO_ID,  // Valor por defecto
        imageUrl: String? = null  // Valor por defecto
    ) {
        val titleText = view.findViewById<TextView>(titleId)
        val descriptionText = view.findViewById<TextView>(descriptionId)
        val btnVeureMes = view.findViewById<TextView>(btnVeureMesId)
        val imageView = if (imageViewId != View.NO_ID) view.findViewById<ImageView>(imageViewId) else null
        val moreText = view.findViewById<TextView>(moreTextId)
        val moreText2 = if (moreText2Id != View.NO_ID) view.findViewById<TextView>(moreText2Id) else null
        val btnVeureMenys = if (btnVeureMenysId != View.NO_ID) view.findViewById<TextView>(btnVeureMenysId) else null

        btnVeureMes.setOnClickListener {
            imageView?.visibility = View.VISIBLE
            moreText.visibility = View.VISIBLE
            moreText2?.visibility = View.VISIBLE
            btnVeureMenys?.visibility = View.VISIBLE
            btnVeureMes.visibility = View.GONE
            imageUrl?.let { url ->
                imageView?.let { iv -> Glide.with(view).load(url).into(iv) }
            }
        }

        btnVeureMenys?.setOnClickListener {
            imageView?.visibility = View.GONE
            moreText.visibility = View.GONE
            moreText2?.visibility = View.GONE
            btnVeureMenys.visibility = View.GONE
            btnVeureMes.visibility = View.VISIBLE
        }
    }
}

