package com.faithjoyfundation.autopilotapi.v1.common.utils;

public class TempPasswordEmailMessage {

    public static String generate(String tempPassword) {
        return String.format(
             "<!DOCTYPE html>"
                     + "<html lang='en'>"
                     + "<head>"
                     + "    <meta charset='UTF-8'>"
                     + "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                     + "    <title>Temporary Password</title>"
                     + "</head>"
                     + "<body style='font-family: Arial, sans-serif;'>"
                     + "    <table width='100%%' cellpadding='10' cellspacing='0' border='0'>"
                     + "        <tr>"
                     + "            <td>"
                     + "                <h2 style='color: #4A4A4A; font-weight: normal;'>Temporary Password</h2>"
                     + "                <p>Dear User,</p>"
                     + "                <p>Below you will find your temporary password:</p>"
                     + "                <p><strong>Password:</strong> <span style='font-family: monospace;'>%s</span></p>"
                     + "                <p>Best regards,</p>"
                     + "                <p><strong>AutoPilot</strong></p>"
                     + "            </td>"
                     + "        </tr>"
                     + "    </table>"
                     + "</body>"
                     + "</html>",
             tempPassword
         );
    }
}
