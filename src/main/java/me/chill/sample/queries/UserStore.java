package me.chill.sample.queries;

import me.chill.SpotifyUser;

public class UserStore {
  private static String accessToken = "BQAHyyQ2rJN3FOCWTHQP_7Ix5fOLhXDAhAvdAEbcxnc8hSYVWlBrkLtFNxL4SyF0N5wqDTBFVsoPLkcGyb0UvYyA6FtKGubat_zN1g7D6KtmM83nX-ylTHfBi4io7cprtIhmviP1NjuhjStN-zIpI1v8h8Nomcb4q1hizmL5DVsvZB6M74Gw51FctWjEneNTifHZ31LFk0CfVkGmOzqEFgejE9-VmUTJqEVPuJEbVuq_ocWFnMGJkNyc-CfV8g9qSX2EXGUL0xAZrB_YdXJRqIN8mT0";

  public static SpotifyUser user = new SpotifyUser(accessToken);
}
