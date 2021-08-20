package link.standen.michael.slideshow.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import link.standen.michael.slideshow.R
import android.content.DialogInterface
import androidx.fragment.app.DialogFragment
import link.standen.michael.slideshow.dialog.ControlsDialog

class ControlsDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bob = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        bob.setView(inflater.inflate(R.layout.dialog_controls, null))
            .setPositiveButton(R.string.changelog_ok_button) { dialog: DialogInterface?, _: Int ->
                dialog?.cancel()
            }
        return bob.create()
    }
}