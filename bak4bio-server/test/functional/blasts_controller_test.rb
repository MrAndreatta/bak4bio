require 'test_helper'

class BlastsControllerTest < ActionController::TestCase
  setup do
    @blast = blasts(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:blasts)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create blast" do
    assert_difference('Blast.count') do
      post :create, blast: { content: @blast.content, database: @blast.database, expect: @blast.expect, gap_costs: @blast.gap_costs, m_scores: @blast.m_scores, max_matches_range: @blast.max_matches_range, max_target_sequence: @blast.max_target_sequence, program: @blast.program, title: @blast.title, word_size: @blast.word_size }
    end

    assert_redirected_to blast_path(assigns(:blast))
  end

  test "should show blast" do
    get :show, id: @blast
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @blast
    assert_response :success
  end

  test "should update blast" do
    put :update, id: @blast, blast: { content: @blast.content, database: @blast.database, expect: @blast.expect, gap_costs: @blast.gap_costs, m_scores: @blast.m_scores, max_matches_range: @blast.max_matches_range, max_target_sequence: @blast.max_target_sequence, program: @blast.program, title: @blast.title, word_size: @blast.word_size }
    assert_redirected_to blast_path(assigns(:blast))
  end

  test "should destroy blast" do
    assert_difference('Blast.count', -1) do
      delete :destroy, id: @blast
    end

    assert_redirected_to blasts_path
  end
end
