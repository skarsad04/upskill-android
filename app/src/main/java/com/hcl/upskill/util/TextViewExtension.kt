package com.hcl.upskill.util

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.hcl.upskill.R
import com.hcl.upskill.ui.auth.login.LoginFragmentDirections

fun TextView.spannableSignup() {
    val span = SpannableString(context.getString(R.string.have_an_account))
    span.setSpan(ContextCompat.getColor(context, R.color.app_theme), 23, span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    val clickSpan: ClickableSpan = object : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) { ds.isUnderlineText = true }
        override fun onClick(textView: View) {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignupFragment())
        }
    }
    span.setSpan(clickSpan, 23, span.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    this.text = span
    this.movementMethod = LinkMovementMethod.getInstance()
}