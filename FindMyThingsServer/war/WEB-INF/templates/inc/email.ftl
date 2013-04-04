<#-- @ftlvariable name="email" type="java.lang.String" -->
<#-- @ftlvariable name="href" type="java.lang.String" -->
<h1>Thanks for being a user of <a href="http://rocket-findmythings.appspot.com">Find My Things</a></h1>

<#if forgot??>

<h2>Ready to reset!</h2>

<p>To regain access to your account, follow the link below to reset your password:</p>

<#else>

<h2>Your account is ready.</h2>

<p>To activate your account, confirm your email address (${email}) by following the link below.</p>

</#if>

<p><a href="${href}">Activate my account</a></p>

<p>Alternatively, if you are unable to click the link, you may copy and paste the following address into your web browser:</p>

<pre>${href}</pre>

<#if forgot??>
<p>Note that if you did not initiate this request, feel free to ignore this message. Your account is safe.</p>
</#if>

<p>Thanks,<br><em>The Find My Things Team</em></p>